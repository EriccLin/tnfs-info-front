package com.tnfs.infoApplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InChargeObj {
    private Long criminalCaseId;
    private Long unitId;
    private String unitName;
    private String unitEngName;
    private Long unitSuperviserId;
    private String unitSuperviserName;
    private String type;
    private String reserve;
    private Boolean notification;
    private List<InChargerObj> inChargers;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InChargeObj that = (InChargeObj) o;
        return Objects.equals(criminalCaseId, that.criminalCaseId) && Objects.equals(unitId, that.unitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(criminalCaseId, unitId);
    }
}
