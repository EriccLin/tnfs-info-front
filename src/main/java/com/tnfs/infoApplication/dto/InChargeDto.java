package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InChargeDto {
    @JsonProperty("criminalCaseId")
    private Long criminalCaseId;
    @JsonProperty("unitId")
    private Long unitId;
    @JsonProperty("unitName")
    private String unitName;
    @JsonProperty("unitEngName")
    private String unitEngName;
    @JsonProperty("unitSuperviserId")
    private Long unitSuperviserId;
    @JsonProperty("unitSuperviserName")
    private String unitSuperviserName;
    @JsonProperty("type")
    private String type;
    @JsonProperty("reserve")
    private String reserve;
    @JsonProperty("notification")
    private Boolean notification;
    @JsonProperty("inChargers")
    private List<InChargerDto> inChargers;

    public InChargeDto(Long criminalCaseId, Long unitId, String unitName, String unitEngName, Long unitSuperviserId, String unitSuperviserName, String type) {
        this.criminalCaseId = criminalCaseId;
        this.unitId = unitId;
        this.unitName = unitName;
        this.unitEngName = unitEngName;
        this.unitSuperviserId = unitSuperviserId;
        this.unitSuperviserName = unitSuperviserName;
        this.type = type;
    }
}
