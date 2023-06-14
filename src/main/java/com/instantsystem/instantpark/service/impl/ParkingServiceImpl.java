package com.instantsystem.instantpark.service.impl;

import com.instantsystem.instantpark.dto.*;
import com.instantsystem.instantpark.exception.ParkingDataException;
import com.instantsystem.instantpark.model.ParkingAvailability;
import com.instantsystem.instantpark.model.ParkingInfo;
import com.instantsystem.instantpark.service.ParkingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ParkingServiceImpl implements ParkingService {

    private final RestTemplate restTemplate;
    @Value("${parking.api.info}")
    private String parkingInfoApiUrl;
    @Value("${parking.api.available}")
    private String parkingAvailabilityApiUrl;

    @Override
    public List<ParkingInfo> getAllParkings() {
        ResponseEntity<ParkingInfoDTO> response = restTemplate.getForEntity(parkingInfoApiUrl, ParkingInfoDTO.class);
        return Objects.requireNonNull(response.getBody()).getRecords().stream()
                .map(this::convertToParkingInfo)
                .collect(Collectors.toList());
    }

    @Override
    public ParkingInfo getParkingById(String parkingId) {
        List<ParkingInfo> parkingInfos = getAllParkings();
        return parkingInfos.stream()
                .filter(parkingInfo -> parkingInfo.getId().equals(parkingId))
                .findFirst()
                .orElseThrow(() -> new ParkingDataException("Parking not found"));
    }

    @Override
    public List<ParkingInfo> getNearbyParkings(Double latitude, Double longitude) {
        List<ParkingInfo> parkingInfos = getAllParkings();
        List<ParkingAvailability> parkingAvailabilities = getAllParkingsAvailabilities();

//        Use a TreeMap to maintain the order of the parkings by their distance to the user
        TreeMap<Double, ParkingInfo> parkingInfoByDistance = new TreeMap<>();

        for (ParkingInfo parkingInfo : parkingInfos) {
            Double parkingLatitude = parkingInfo.getGeoPoint2D()[0];
            Double parkingLongitude = parkingInfo.getGeoPoint2D()[1];
            Double distance = calculateDistance(latitude, longitude, parkingLatitude, parkingLongitude);

//            Add only if the parking is available
            if (isParkingAvailable(parkingInfo.getParkingName(), parkingAvailabilities)) {
                parkingInfo.setDistance(distance * 1000);
                parkingInfoByDistance.put(distance, parkingInfo);
            }
        }

        return new ArrayList<>(parkingInfoByDistance.values());
    }

    private boolean isParkingAvailable(String parkingName, List<ParkingAvailability> parkingAvailabilities) {
//        check if the parking is available and availableSpots != capacity
        return parkingAvailabilities.stream()
                .anyMatch(parkingAvailability -> parkingAvailability.getParkingName().equals(parkingName)
                        && !Objects.equals(parkingAvailability.getAvailableSpots(), parkingAvailability.getCapacity()));

    }

    private Double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        // The radius of the earth in kilometers
        final int EARTH_RADIUS = 6371;

        // Convert degrees to radians
        double latDistance = Math.toRadians(latitude2 - latitude1);
        double lonDistance = Math.toRadians(longitude2 - longitude1);

        // Haversine formula
//        a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
//        c = 2 ⋅ atan2( √a, √(1−a) )
//        d = R ⋅ c
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    @Override
    public List<ParkingInfo> getParkingsByName(String name) {
        List<ParkingInfo> parkingInfos = getAllParkings();
        return parkingInfos.stream()
                .filter(parkingInfo -> parkingInfo.getParkingName().contains(name.toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public ParkingAvailability getParkingAvailability(String parkingName) {
        ResponseEntity<ParkingAvailabilityDTO> response = restTemplate.getForEntity(parkingAvailabilityApiUrl, ParkingAvailabilityDTO.class);

        AvailabilityParkingFieldsDTO fields = Objects.requireNonNull(response.getBody()).getRecords().stream()
                .map(AvailabilityRecordDTO::getFields)
                .filter(f -> f.getParkingName().equals(parkingName))
                .findFirst()
                .orElseThrow(() -> new ParkingDataException("Parking availability not found"));

        return convertToParkingAvailability(fields);
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
