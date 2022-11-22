package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PersonDto {
    @JsonProperty("id")
    private Long id = null;
    @JsonProperty("name")
    private String name = null;
    @JsonProperty("idNumber")
    private String idNumber = null;
    @JsonProperty("birthdate")
    private LocalDate birthdate = null;
    @JsonProperty("isActive")
    private Boolean isActive = null;
    @JsonProperty("gender")
    private String gender = null;

}
