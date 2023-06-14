package com.instantsystem.instantpark.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingFieldsDTO {
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
}
