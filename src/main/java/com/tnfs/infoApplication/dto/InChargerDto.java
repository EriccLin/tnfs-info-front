package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InChargerDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("criminalCaseId")
    private Long criminalCaseId;
    @JsonProperty("unitId")
    private Long unitId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("mrank")
    private String mrank;
    @JsonProperty("type")
    private String type;
    @JsonProperty("dispatch")
    private LocalDate dispatch;
}
