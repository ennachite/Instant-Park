package com.instantsystem.instantpark.service.impl;

import com.instantsystem.instantpark.dto.*;
import com.instantsystem.instantpark.exception.ParkingDataException;
import com.instantsystem.instantpark.model.ParkingAvailability;
import com.instantsystem.instantpark.model.ParkingInfo;
import com.instantsystem.instantpark.service.ParkingService;
import com.instantsystem.instantpark.util.LoggingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@CommonsLog
public class ParkingServiceImpl implements ParkingService {

    private final RestTemplate restTemplate;
    @Value("${parking.api.info}")
    private String parkingInfoApiUrl;
    @Value("${parking.api.available}")
    private String parkingAvailabilityApiUrl;

    /**
     * Fetches all the parkings from the remote API
     * @return list of all ParkingInfo
     */
    @Override
    public List<ParkingInfo> getAllParkings() {
        ResponseEntity<ParkingInfoDTO> response = restTemplate.getForEntity(parkingInfoApiUrl, ParkingInfoDTO.class);
        return Objects.requireNonNull(response.getBody()).getRecords().stream()
                .map(this::convertToParkingInfo)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific parking information by its id
     * @param parkingId the id of the parking
     * @return ParkingInfo for the requested parking id
     */
    @Override
    public ParkingInfo getParkingById(String parkingId) {
        log.info(LoggingUtils.getMessage(parkingId));
        List<ParkingInfo> parkingInfos = getAllParkings();
        return parkingInfos.stream()
                .filter(parkingInfo -> parkingInfo.getId().equals(parkingId))
                .findFirst()
                .orElseThrow(() -> new ParkingDataException("Parking not found"));
    }

    /**
     * Retrieves nearby available parkings based on the provided latitude and longitude
     * @param latitude the latitude of the current location
     * @param longitude the longitude of the current location
     * @return list of nearby ParkingInfo
     */
    @Override
    public List<ParkingInfo> getNearbyParkings(Double latitude, Double longitude) {
        log.info(LoggingUtils.getMessage(latitude, longitude));
        List<ParkingInfo> parkingInfos = getAllParkings();
        List<ParkingAvailability> parkingAvailabilities = getAllParkingsAvailabilities();

        TreeMap<Double, ParkingInfo> parkingInfoByDistance = new TreeMap<>();

        for (ParkingInfo parkingInfo : parkingInfos) {
            Double parkingLatitude = parkingInfo.getGeoPoint2D()[0];
            Double parkingLongitude = parkingInfo.getGeoPoint2D()[1];
            Double distance = calculateDistance(latitude, longitude, parkingLatitude, parkingLongitude);

            if (isParkingAvailable(parkingInfo.getParkingName(), parkingAvailabilities)) {
                parkingInfo.setDistance(distance * 1000);
                parkingInfoByDistance.put(distance, parkingInfo);
            }
        }

        return new ArrayList<>(parkingInfoByDistance.values());
    }

    /**
     * Retrieves parkings by their names
     * @param name the name of the parking
     * @return list of ParkingInfo with the provided name
     */
    @Override
    public List<ParkingInfo> getParkingsByName(String name) {
        log.info(LoggingUtils.getMessage(name));
        List<ParkingInfo> parkingInfos = getAllParkings();
        return parkingInfos.stream()
                .filter(parkingInfo -> parkingInfo.getParkingName().contains(name.toUpperCase()))
                .collect(Collectors.toList());
    }

    private boolean isParkingAvailable(String parkingName, List<ParkingAvailability> parkingAvailabilities) {
        return parkingAvailabilities.stream()
                .anyMatch(parkingAvailability -> parkingAvailability.getParkingName().equals(parkingName)
                        && !Objects.equals(parkingAvailability.getAvailableSpots(), parkingAvailability.getCapacity()));
    }

    private Double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        final int EARTH_RADIUS = 6371;

        double latDistance = Math.toRadians(latitude2 - latitude1);
        double lonDistance = Math.toRadians(longitude2 - longitude1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    private List<ParkingAvailability> getAllParkingsAvailabilities() {
        ResponseEntity<ParkingAvailabilityDTO> response = restTemplate.getForEntity(parkingAvailabilityApiUrl, ParkingAvailabilityDTO.class);
        return Objects.requireNonNull(response.getBody()).getRecords().stream()
                .map(AvailabilityRecordDTO::getFields)
                .map(this::convertToParkingAvailability)
                .collect(Collectors.toList());
    }

    private ParkingInfo convertToParkingInfo(RecordDTO record) {
        return ParkingInfo.builder()
                .id(record.getFields().getId())
                .parkingName(record.getFields().getParkingName())
                .totalSpots(record.getFields().getTotalSpots())
                .geoPoint2D(record.getFields().getGeoPoint2D())
                .address(record.getFields().getAddress())
                .info(record.getFields().getInfo())
                .tarif1h(record.getFields().getTarif1h())
                .build();
    }

    private ParkingAvailability convertToParkingAvailability(AvailabilityParkingFieldsDTO fields) {
        return ParkingAvailability.builder()
                .parkingName(fields.getParkingName())
                .availableSpots(fields.getAvailableSpots())
                .occupancyRate(fields.getOccupancyRate())
                .capacity(fields.getCapacity())
                .geoPoint2D(fields.getGeoPoint2D())
                .lastUpdate(fields.getLastUpdate())
                .build();
    }
}
