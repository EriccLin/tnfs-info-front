package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TimeLocalityDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("criminalCaseId")
    private Long criminalCaseId;
    @JsonProperty("stime")
    private LocalDateTime stime;
    @JsonProperty("etime")
    private LocalDateTime etime;
    @JsonProperty("location")
    private String location;
    @JsonProperty("type")
    private String type;
    @JsonProperty("reserve")
    private String reserve;

}
