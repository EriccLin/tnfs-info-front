package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CriminalCaseDto {
    @JsonProperty("id")
    private Long id = null;
    @JsonProperty("code")
    private String code = null;
    @JsonProperty("name")
    private String name = null;
    @JsonProperty("content")
    private String content = null;
    @JsonProperty("isActive")
    private Boolean isActive = null;
    @JsonProperty("createdAt")
    private LocalDateTime createdAt = null;
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt = null;
    @JsonProperty("reserve")
    private String reserve = null;
    @JsonProperty("inCharges")
    private Set<InChargeDto> inCharges = null;
    @JsonProperty("personInvolveds")
    private List<PersonInvolvedDto> personInvolveds = null;
    @JsonProperty("investigations")
    private List<InvestigationDto> investigations = null;
    @JsonProperty("evidences")
    private List<EvidenceDto> evidences = null;
    @JsonProperty("timeLocalities")
    private List<TimeLocalityDto> timeLocalities = null;
    @JsonProperty("caseTypes")
    private Set<CaseTypeDto> caseTypes = null;
}
