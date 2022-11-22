package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CaseTypeDto {
    @JsonProperty("id")
    private Long id = null;
    @JsonProperty("name")
    private String name = null;
    @JsonProperty("code")
    private String code = null;
    @JsonProperty("type")
    private Integer type = 0;

    public CaseTypeDto(Long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }
}