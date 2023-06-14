package com.instantsystem.instantpark.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityParkingFieldsDTO {

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
