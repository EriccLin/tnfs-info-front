package com.tnfs.infoApplication.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemObj {
    private Long id;
    private Long evidenceId;
    private String name;
    private Integer quantity;
    private String qualifier;
    private String reserve;
}
