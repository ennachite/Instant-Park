package com.instantsystem.instantpark.exception;

public class ParkingDataException extends RuntimeException {

    public ParkingDataException(String message) {
        super(message);
    }

    public ParkingDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
