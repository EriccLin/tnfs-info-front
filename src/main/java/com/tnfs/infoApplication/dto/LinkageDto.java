package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class LinkageDto {
    @JsonProperty("examinationId")
    private Long examinationId;
    @JsonProperty("personId")
    private Long personId;
    @JsonProperty("record")
    private String record;
    @JsonProperty("reserve")
    private String reserve;
}
