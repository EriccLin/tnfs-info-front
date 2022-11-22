package com.tnfs.infoApplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriminalCaseObj {
    private Long id;
    private String name;
    private String code;
    private String content;
    private Boolean isActive;
    private String reserve;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<InChargeObj> inCharges;
    private List<PersonInvolvedObj> personInvolveds;
    private List<InvestigationObj> investigations;
    private List<EvidenceObj> evidences;
    private List<TimeLocalityObj> timeLocalities;
    private Set<CaseTypeObj> caseTypes;
}
