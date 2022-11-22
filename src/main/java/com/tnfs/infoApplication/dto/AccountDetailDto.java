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
public class AccountDetailDto extends AccountDto{
    @JsonProperty("password")
    protected String password = null;
    @JsonProperty("personIdNumber")
    protected String personIdNumber;

    public AccountDetailDto(Long id, String name, String email, String status, LocalDateTime createdAt, LocalDateTime updatedAt, Long personId, String personName, Boolean personIsActive, LocalDate personBirthdate, String personIdNumber, String password, Collection<RoleDto> roles) {
        super(id, name, email, status, createdAt, updatedAt, personId, personName, personIsActive, personBirthdate, roles);
        this.password = password;
        this.personIdNumber = personIdNumber;
    }

    public AccountDetailDto(Long id, String name, String email, String status, LocalDateTime createdAt, LocalDateTime updatedAt, Long personId, String personName, Boolean personIsActive, LocalDate personBirthdate, String personIdNumber, String password) {
        super(id, name, email, status, createdAt, updatedAt, personId, personName, personIsActive, personBirthdate, null);
        this.password = password;
        this.personIdNumber = personIdNumber;
    }
}
