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
public class ParkingInfo {
    @JsonProperty("id")
    private String id;

    @JsonProperty("nom_du_par")
    private String parkingName;

    @JsonProperty("nb_places")
    private Integer totalSpots;

    @JsonProperty("geo_point_2d")
    private Double[] geoPoint2D;

    @JsonProperty("adresse")
    private String address;

    @JsonProperty("info")
    private String info;

    @JsonProperty("tarif_1h")
    private String tarif1h;

    @JsonProperty("distance")
    private Double distance;
}
