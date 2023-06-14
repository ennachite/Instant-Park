package com.instantsystem.instantpark.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingAvailability {

    @JsonProperty("nom_du_parking")
    private String parkingName;

    @JsonProperty("nb_places")
    private Integer availableSpaces;

    @JsonProperty("taux_doccupation")
    private Double occupancyRate;
}
