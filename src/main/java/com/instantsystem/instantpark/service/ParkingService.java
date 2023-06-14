package com.instantsystem.instantpark.service;

import com.instantsystem.instantpark.model.ParkingAvailability;
import com.instantsystem.instantpark.model.ParkingInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParkingService {
    List<ParkingInfo> getAllParkings();

    ParkingInfo getParkingById(String parkingId);

    List<ParkingInfo> getNearbyParkings(Double latitude, Double longitude);

    List<ParkingInfo> getParkingsByName(String name);

    ParkingAvailability getParkingAvailability(String parkingId);
}
