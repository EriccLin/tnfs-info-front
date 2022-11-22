package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CollectionSheetDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("submitTime")
    private LocalDateTime submitTime;
    @JsonProperty("type")
    private String type;
    @JsonProperty("code")
    private String code;
    @JsonProperty("reserve")
    private String reserve;
    @JsonProperty("evidences")
    private List<EvidenceDto> evidences;
    @JsonProperty("examinations")
    private List<ExaminationDto> examinations;
}
