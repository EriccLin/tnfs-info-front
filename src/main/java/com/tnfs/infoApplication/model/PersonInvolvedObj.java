package com.tnfs.infoApplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.tnfs.infoApplication.util.MyUtil.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonInvolvedObj {
    public static String[] PERSON_INVOLVED_TYPES = {"報案人","被害人","涉嫌人","當事人","被害人親友","涉嫌人親友","當事人親友","證人","其他"};

    private static final List<String> types = Arrays.asList(PERSON_INVOLVED_TYPES);

    private Long criminalCaseId;
    private Long personId;
    private String name;
    private LocalDate birthdate;
    private String idNumber;
    private Boolean isActive;
    private Boolean isUnknown;
    private String country;
    private String type;
    private String reserve;

    public String getMingGougDate() {
        if (birthdate != null) {
            return convert2MingGoug(birthdate);
        }
        return "";
    }

    public static List<String> getTypes() {
        return types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonInvolvedObj obj = (PersonInvolvedObj) o;
        return Objects.equals(criminalCaseId, obj.criminalCaseId) && Objects.equals(idNumber, obj.idNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(criminalCaseId, idNumber);
    }
}
