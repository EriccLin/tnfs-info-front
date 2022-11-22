package com.tnfs.infoApplication.model;

import lombok.*;

import java.time.LocalDate;

import static com.tnfs.infoApplication.util.MyUtil.convert2MingGoug;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InChargerObj {
    private Long id;
    private Long criminalCaseId;
    private Long unitId;
    private String name;
    private String mrank;
    private String type;
    private LocalDate dispatch;


    public String getMingGougDispatchDate() {
        if (dispatch != null) {
            return convert2MingGoug(dispatch);
        }
        return "";
    }
}
