package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InvestigationMemberDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("investigationId")
    private Long investigationId;
    @JsonProperty("memberId")
    private Long memberId;
    @JsonProperty("mrank")
    private String mrank = null;
    @JsonProperty("personId")
    private Long personId = null;
    @JsonProperty("personName")
    private String personName = null;
    @JsonProperty("unitId")
    private Long unitId = null;
    @JsonProperty("unitName")
    private String unitName = null;
    @JsonProperty("unitSuperviserId")
    private Long unitSuperviserId = null;
    @JsonProperty("unitSuperviserName")
    private String unitSuperviserName = null;
    @JsonProperty("unitLevel")
    private Integer unitLevel = null;
    @JsonProperty("task")
    private String task;
}