package com.instantsystem.instantpark.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingInfoDTO {
    @JsonProperty("nhits")
    private Integer nhits;

    @JsonProperty("records")
    private List<RecordDTO> records;
}
