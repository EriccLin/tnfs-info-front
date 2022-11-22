package com.tnfs.infoApplication.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailObj extends AccountObj {
    private String password;
    private String personIdNumber;
    public AccountDetailObj(Long id, String name, String email, String status, LocalDateTime createdAt, LocalDateTime updatedAt, Long personId, String personName, Boolean personIsActive, LocalDate personBirthdate, String personIdNumber, String password) {
        super(id, name, email, status, createdAt, updatedAt, personId, personName, personIsActive, personBirthdate);
        this.password = password;
        this.personIdNumber = personIdNumber;
    }
}
