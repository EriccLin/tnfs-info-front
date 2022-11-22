package com.tnfs.infoApplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvidenceObj {
    private Long id;
    private Long criminalCaseId;
    private List<ItemObj> items;
    private LocalDateTime start;
    private String location;
    private String type;
    private String code;
    private String reserve;
}
