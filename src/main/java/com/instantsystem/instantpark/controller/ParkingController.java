package com.instantsystem.instantpark.controller;

import com.instantsystem.instantpark.model.ParkingAvailability;
import com.instantsystem.instantpark.model.ParkingInfo;
import com.instantsystem.instantpark.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/parkinginfo")
    public ResponseEntity<List<ParkingInfo>> getParkingInfo() {
        List<ParkingInfo> parkingInfo = parkingService.getParkingInfo();
        return ResponseEntity.ok(parkingInfo);
    }

    @GetMapping("/parkingavailability")
    public ResponseEntity<List<ParkingAvailability>> getParkingAvailability() {
        List<ParkingAvailability> parkingAvailability = parkingService.getParkingAvailability();
        return ResponseEntity.ok(parkingAvailability);
    }
}
