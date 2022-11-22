package com.tnfs.infoApplication.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvestigationObj {
    private Long id;
    private Long criminalCaseId;
    private LocalDate stime;
    private LocalDate etime;
    private String location;
    private String type;
    private List<InvestigationMemberObj> investigationMembers;
}
