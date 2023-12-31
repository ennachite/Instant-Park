package com.instantsystem.instantpark.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingAvailability {

    @JsonProperty("nom")
    private String parkingName;

    @JsonProperty("places")
    private Integer availableSpots;

    @JsonProperty("taux_doccupation")
    private Double occupancyRate;

    @JsonProperty("capacite")
    private Integer capacity;

    @JsonProperty("geo_point_2d")
    private Double[] geoPoint2D;

    @JsonProperty("derniere_actualisation_bo")
    private String lastUpdate;
}
