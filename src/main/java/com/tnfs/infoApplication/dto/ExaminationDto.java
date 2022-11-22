package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ExaminationDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("evidenceId")
    private Long evidenceId;
    @JsonProperty("collectionSheetId")
    private Long collectionSheetId;
    @JsonProperty("stime")
    private LocalDateTime stime;
    @JsonProperty("etime")
    private LocalDateTime etime;
    @JsonProperty("record")
    private String record;
    @JsonProperty("isPreExam")
    private Boolean isPreExam = false;
    @JsonProperty("reserve")
    private String reserve;
    @JsonProperty("linkPrevious")
    private Set<ExaminationDto> linkPreviouss;
    @JsonProperty("expertReports")
    private Set<ExpertReportDto> expertReports;
    @JsonProperty("linkages")
    private Set<LinkageDto> linkages;

}
