package com.instantsystem.instantpark.controller;

import com.instantsystem.instantpark.model.ParkingAvailability;
import com.instantsystem.instantpark.model.ParkingInfo;
import com.instantsystem.instantpark.service.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parkings")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping
    public ResponseEntity<List<ParkingInfo>> getAllParkings() {
        List<ParkingInfo> parkingsInfo = parkingService.getAllParkings();
        return ResponseEntity.ok(parkingsInfo);
    }

    @GetMapping("/{parkingId}")
    public ResponseEntity<ParkingInfo> getParkingById(@PathVariable("parkingId") String parkingId) {
        ParkingInfo parkingInfo = parkingService.getParkingById(parkingId);
        return ResponseEntity.ok(parkingInfo);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<ParkingInfo>> getNearbyParkings(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude
    ) {
        List<ParkingInfo> parkingsInfo = parkingService.getNearbyParkings(latitude, longitude);
        return ResponseEntity.ok(parkingsInfo);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ParkingInfo>> getParkingsByName(@RequestParam("nom") String name) {
        List<ParkingInfo> parkingsInfo = parkingService.getParkingsByName(name);
        return ResponseEntity.ok(parkingsInfo);
    }

    @GetMapping("/{parkingName}/availability")
    public ResponseEntity<ParkingAvailability> getParkingAvailability(@PathVariable("parkingName") String parkingName) {
        ParkingAvailability parkingAvailability = parkingService.getParkingAvailability(parkingName);
        return ResponseEntity.ok(parkingAvailability);
    }
}
