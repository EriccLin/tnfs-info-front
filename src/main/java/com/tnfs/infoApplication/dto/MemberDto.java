package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDto {
    @JsonProperty("id")
    private Long id = null;
    @JsonProperty("sdate")
    private LocalDate sdate = null;
    @JsonProperty("edate")
    private LocalDate edate = null;
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
}
