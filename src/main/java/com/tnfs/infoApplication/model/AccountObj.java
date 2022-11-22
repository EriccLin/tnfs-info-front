package com.tnfs.infoApplication.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountObj {
    private Long id;
    private String name;
    private String email;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long personId;
    private String personName;
    private Boolean personIsActive;
    private LocalDate personBirthdate;
    private List<RoleObj> roles;

    public AccountObj(Long id, String name, String email, String status, LocalDateTime createdAt, LocalDateTime updatedAt, Long personId, String personName, Boolean personIsActive, LocalDate personBirthdate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.personId = personId;
        this.personName = personName;
        this.personIsActive = personIsActive;
        this.personBirthdate = personBirthdate;
    }
}
