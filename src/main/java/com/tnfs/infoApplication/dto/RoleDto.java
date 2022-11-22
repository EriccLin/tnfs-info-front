package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RoleDto {
    @JsonProperty("id")
    protected Long id = null;
    @JsonProperty("name")
    protected String name = null;
}
