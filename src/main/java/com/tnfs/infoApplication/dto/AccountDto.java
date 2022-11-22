package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AccountDto {
    @JsonProperty("id")
    protected Long id = null;
    @JsonProperty("name")
    protected String name = null;
    @JsonProperty("email")
    protected String email = null;
    @JsonProperty("status")
    protected String status;
    @JsonProperty("createdAt")
    protected LocalDateTime createdAt = null;
    @JsonProperty("updatedAt")
    protected LocalDateTime updatedAt = null;
    // Person Entity
    @JsonProperty("personId")
    protected Long personId;
    @JsonProperty("personName")
    protected String personName;
    @JsonProperty("personIsActive")
    protected Boolean personIsActive;
    @JsonProperty("personBirthdate")
    protected LocalDate personBirthdate;
    @JsonProperty("roles")
    private Collection<RoleDto> roles = null;
}
