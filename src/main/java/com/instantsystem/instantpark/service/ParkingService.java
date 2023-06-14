package com.instantsystem.instantpark.service;

import com.instantsystem.instantpark.model.ParkingAvailability;
import com.instantsystem.instantpark.model.ParkingInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParkingService {
    List<ParkingInfo> getParkingInfo();
    List<ParkingAvailability> getParkingAvailability();
}
