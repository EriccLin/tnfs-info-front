package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ExpertReportDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("code")
    private String code;
    @JsonProperty("stime")
    private LocalDateTime stime;
    @JsonProperty("etime")
    private LocalDateTime etime;
    @JsonProperty("content")
    private String content;
    @JsonProperty("reserve")
    private String reserve;
    @JsonProperty("examinations")
    private Set<ExaminationDto> examinations;

}
