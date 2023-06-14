package com.instantsystem.instantpark.controller;

import com.instantsystem.instantpark.model.ParkingInfo;
import com.instantsystem.instantpark.service.ParkingService;
import com.instantsystem.instantpark.util.LoggingUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parkings")
@CommonsLog
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping
    public ResponseEntity<List<ParkingInfo>> getAllParkings() {
        log.info(LoggingUtils.getStartMessage());
        List<ParkingInfo> parkingsInfo = parkingService.getAllParkings();
        log.info(LoggingUtils.getEndMessage(parkingsInfo));
        return ResponseEntity.ok(parkingsInfo);
    }

    @GetMapping("/{parkingId}")
    public ResponseEntity<ParkingInfo> getParkingById(@PathVariable("parkingId") String parkingId) {
        log.info(LoggingUtils.getStartMessage());
        ParkingInfo parkingInfo = parkingService.getParkingById(parkingId);
        log.info(LoggingUtils.getEndMessage(parkingInfo));
        return ResponseEntity.ok(parkingInfo);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<ParkingInfo>> getNearbyParkings(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude
    ) {
        log.info(LoggingUtils.getStartMessage());
        List<ParkingInfo> parkingsInfo = parkingService.getNearbyParkings(latitude, longitude);
        log.info(LoggingUtils.getEndMessage(parkingsInfo));
        return ResponseEntity.ok(parkingsInfo);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ParkingInfo>> getParkingsByName(@RequestParam("nom") String name) {
        log.info(LoggingUtils.getStartMessage());
        List<ParkingInfo> parkingsInfo = parkingService.getParkingsByName(name);
        log.info(LoggingUtils.getEndMessage(parkingsInfo));
        return ResponseEntity.ok(parkingsInfo);
    }

//    @GetMapping("/{parkingName}/availability")
//    public ResponseEntity<ParkingAvailability> getParkingAvailability(@PathVariable("parkingName") String parkingName) {
//        ParkingAvailability parkingAvailability = parkingService.getParkingAvailability(parkingName);
//        return ResponseEntity.ok(parkingAvailability);
//    }
}
