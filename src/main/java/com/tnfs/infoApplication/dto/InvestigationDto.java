package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InvestigationDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("criminalCaseId")
    private Long criminalCaseId;
    @JsonProperty("stime")
    private LocalDate stime;
    @JsonProperty("etime")
    private LocalDate etime;
    @JsonProperty("location")
    private String location;
    @JsonProperty("type")
    private String type;
    @JsonProperty("investigationMembers")
    private List<InvestigationMemberDto> investigationMembers;
}
