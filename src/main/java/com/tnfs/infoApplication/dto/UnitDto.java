package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UnitDto {
    @JsonProperty("id")
    private Long id = null;
    @JsonProperty("name")
    private String name = null;
    @JsonProperty("engName")
    private String engName = null;
    @JsonProperty("superviserId")
    private Long superviserId = null;
    @JsonProperty("address")
    private String address = null;
    @JsonProperty("level")
    private Integer level = null;
}
