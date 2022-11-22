package com.tnfs.infoApplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.tnfs.infoApplication.util.MyUtil.convert2MingGoug;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeLocalityObj {
    private Long id;
    private Long criminalCaseId;
    private LocalDateTime stime;
    private LocalDateTime etime;
    private String location;
    private String type;
    private String reserve;

    public String getDateTimeRange() {
        if ( stime != null) {
            String endStr = etime != null ? " ~ " + convert2MingGoug(etime) : "";
            return convert2MingGoug(stime) + endStr;
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) return false;
        TimeLocalityObj that = (TimeLocalityObj) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
