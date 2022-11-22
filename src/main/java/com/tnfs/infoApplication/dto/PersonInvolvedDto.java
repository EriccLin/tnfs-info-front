package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PersonInvolvedDto {
    @JsonProperty("criminalCaseId")
    private Long criminalCaseId = null;
    @JsonProperty("personId")
    private Long personId = null;
    @JsonProperty("name")
    private String name = null;
    @JsonProperty("birthdate")
    private LocalDate birthdate = null;
    @JsonProperty("idNumber")
    private String idNumber = null;
    @JsonProperty("isActive")
    private Boolean isActive = true;
    @JsonProperty("isUnknown")
    private Boolean isUnknown = false;
    @JsonProperty("country")
    private String country = null;
    @JsonProperty("type")
    private String type = null;
    @JsonProperty("reserve")
    private String reserve = null;
}
