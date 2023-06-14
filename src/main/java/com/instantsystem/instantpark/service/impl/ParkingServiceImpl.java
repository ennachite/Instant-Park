package com.instantsystem.instantpark.service.impl;

import com.instantsystem.instantpark.exception.ParkingDataException;
import com.instantsystem.instantpark.model.ParkingAvailability;
import com.instantsystem.instantpark.model.ParkingInfo;
import com.instantsystem.instantpark.repository.ParkingRepository;
import com.instantsystem.instantpark.service.ParkingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingServiceImpl implements ParkingService {
    private final ParkingRepository parkingRepository;

    @Override
    public List<ParkingInfo> getParkingInfo() {
        log.info("Fetching parking information");
        try {
            log.info("Fetching parking information try");
            return parkingRepository.fetchParkingInfo();
        } catch (Exception e) {
            throw new ParkingDataException("Failed to fetch parking information");
        }
    }

    @Override
    public List<ParkingAvailability> getParkingAvailability() {
        try {
            return parkingRepository.fetchParkingAvailability();
        } catch (Exception e) {
            throw new ParkingDataException("Failed to fetch parking availability");
        }
    }
}
