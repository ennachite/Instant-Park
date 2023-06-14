package com.instantsystem.instantpark.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTO {
    @JsonProperty("recordid")
    private String recordId;

    @JsonProperty("fields")
    private ParkingFieldsDTO fields;
}
