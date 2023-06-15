package com.instantsystem.instantpark.controller;

import com.instantsystem.instantpark.model.ParkingInfo;
import com.instantsystem.instantpark.service.ParkingService;
import com.instantsystem.instantpark.util.LoggingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parkings")
@RequiredArgsConstructor
@CommonsLog
public class ParkingController {

    private final ParkingService parkingService;

    /**
     * Returns a list of all parkings
     *
     * @return ResponseEntity with list of ParkingInfo
     */
    @GetMapping
    public ResponseEntity<List<ParkingInfo>> getAllParkings() {
        log.info(LoggingUtils.getStartMessage());
        List<ParkingInfo> parkingsInfo = parkingService.getAllParkings();
        log.info(LoggingUtils.getEndMessage(parkingsInfo));
        return ResponseEntity.ok(parkingsInfo);
    }

    /**
     * Returns a specific parking by its id
     *
     * @param parkingId the id of the parking
     * @return ResponseEntity with ParkingInfo
     */
    @GetMapping("/{parkingId}")
    public ResponseEntity<ParkingInfo> getParkingById(@PathVariable String parkingId) {
        log.info(LoggingUtils.getStartMessage());
        ParkingInfo parkingInfo = parkingService.getParkingById(parkingId);
        log.info(LoggingUtils.getEndMessage(parkingInfo));
        return ResponseEntity.ok(parkingInfo);
    }

    /**
     * Returns a list of nearby available parkings based on the provided latitude and longitude
     *
     * @param latitude  of the current location
     * @param longitude of the current location
     * @return ResponseEntity with list of nearby ParkingInfo + distance (in meters)
     */
    @GetMapping("/nearby")
    public ResponseEntity<List<ParkingInfo>> getNearbyParkings(
            @RequestParam Double latitude,
            @RequestParam Double longitude
    ) {
        log.info(LoggingUtils.getStartMessage());
        List<ParkingInfo> parkingsInfo = parkingService.getNearbyParkings(latitude, longitude);
        log.info(LoggingUtils.getEndMessage(parkingsInfo));
        return ResponseEntity.ok(parkingsInfo);
    }

    /**
     * Returns a list of parkings by their names
     *
     * @param name the name of the parking
     * @return ResponseEntity with list of ParkingInfo with the provided name
     */
    @GetMapping("/search")
    public ResponseEntity<List<ParkingInfo>> getParkingsByName(@RequestParam String name) {
        log.info(LoggingUtils.getStartMessage());
        List<ParkingInfo> parkingsInfo = parkingService.getParkingsByName(name);
        log.info(LoggingUtils.getEndMessage(parkingsInfo));
        return ResponseEntity.ok(parkingsInfo);
    }
}
