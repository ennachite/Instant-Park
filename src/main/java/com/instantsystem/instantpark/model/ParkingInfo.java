package com.instantsystem.instantpark.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingInfo {

    @JsonProperty("nom_du_parking")
    private String parkingName;

    @JsonProperty("nb_places")
    private Integer totalSpaces;

    @JsonProperty("geo_point_2d")
    private Double[] location;

    @JsonProperty("type_ouvra")
    private String type;

    @JsonProperty("adresse")
    private String address;
}
