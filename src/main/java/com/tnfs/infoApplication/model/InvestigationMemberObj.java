package com.tnfs.infoApplication.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvestigationMemberObj {
    private Long id;
    private Long investigationId;
    private Long memberId;
    private String mrank;
    private Long personId;
    private String personName;
    private Long unitId;
    private String unitName;
    private Long unitSuperviserId;
    private String unitSuperviserName;
    private Integer unitLevel;
    private String task;
}
