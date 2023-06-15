package com.instantsystem.instantpark.service;

import com.instantsystem.instantpark.model.ParkingInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for operations related to parkings.
 */
@Service
public interface ParkingService {

    /**
     * Fetches all available parkings.
     *
     * @return list of all parkings
     */
    List<ParkingInfo> getAllParkings();

    /**
     * Retrieves a specific parking by its id.
     *
     * @param parkingId id of the parking
     * @return the parking info
     */
    ParkingInfo getParkingById(String parkingId);

    /**
     * Retrieves nearby available parkings based on a geographical point defined by latitude and longitude.
     *
     * @param latitude  the latitude of the point
     * @param longitude the longitude of the point
     * @return list of nearby parkings
     */
    List<ParkingInfo> getNearbyParkings(Double latitude, Double longitude);

    /**
     * Searches for parkings by their name.
     *
     * @param name the name of the parking
     * @return list of parkings matching the name
     */
    List<ParkingInfo> getParkingsByName(String name);
}
