package com.instantsystem.instantpark.repository;

import com.instantsystem.instantpark.exception.ParkingDataException;
import com.instantsystem.instantpark.model.ParkingAvailability;
import com.instantsystem.instantpark.model.ParkingInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Repository
@Slf4j
public class ParkingRepository {

    private final RestTemplate restTemplate;

    public ParkingRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ParkingInfo> fetchParkingInfo() {
        log.info("Fetching parking information repository");
        try {
            String apiUrl = "https://data.grandpoitiers.fr/api/records/1.0/search/";
            URI uri = UriComponentsBuilder.fromUriString(apiUrl)
                    .queryParam("dataset", "mobilite-parkings-grand-poitiers-donnees-metiers")
                    .queryParam("rows", 1000)
                    .queryParam("facet", "nom_du_parking")
                    .queryParam("facet", "zone_tarifaire")
                    .queryParam("facet", "statut2")
                    .queryParam("facet", "statut3")
                    .build()
                    .toUri();

            log.info("Fetching parking information uri: " + uri);

            ParkingInfo[] parkingInfoArray = restTemplate.getForObject(uri, ParkingInfo[].class);

            log.info("Fetching parking information parkingInfoArray: " + Arrays.toString(parkingInfoArray));
            assert parkingInfoArray != null;
            return Arrays.asList(parkingInfoArray);
        } catch (Exception e) {
            throw new ParkingDataException("Failed to fetch parking information");
        }
    }

    public List<ParkingAvailability> fetchParkingAvailability() {
        try {
            String apiUrl = "https://data.grandpoitiers.fr/api/records/1.0/search/";
            URI uri = UriComponentsBuilder.fromUriString(apiUrl)
                    .queryParam("dataset", "mobilites-stationnement-des-parkings-en-temps-reel")
                    .queryParam("facet", "nom")
                    .build()
                    .toUri();

            ParkingAvailability[] parkingAvailabilityArray = restTemplate.getForObject(uri, ParkingAvailability[].class);
            assert parkingAvailabilityArray != null;
            return Arrays.asList(parkingAvailabilityArray);
        } catch (Exception e) {
            throw new ParkingDataException("Failed to fetch parking information");
        }
    }
}
