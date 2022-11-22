package com.tnfs.infoApplication.model;

import lombok.*;

import java.time.LocalDate;

import static com.tnfs.infoApplication.util.MyUtil.convert2MingGoug;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonObj {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private String idNumber;
    private Boolean isActive;
    private String gender;

    public String getMingGougBirthdate() {
        if (birthdate != null) {
            return convert2MingGoug(birthdate);
        }
        return "";
    }

    public String getPersonInfo() {
        return name==null ? "" : name +
                "、性別：" + gender==null ? "" : gender +
                "、生日：" + (birthdate == null ? "不詳" : getMingGougBirthdate()) +
                "、證號：" + (idNumber==null ? "" : idNumber) +  (isActive!=null && !isActive  ? "、已歿" : "");
    }

    @Override
    public String toString() {
        return getPersonInfo();
    }
}
