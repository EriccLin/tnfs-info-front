package com.tnfs.infoApplication.model;

import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaseTypeObj {
    public static String[] CASE_TYPE_PRIMARY = {"死亡","一般刑案","重大刑案","公共危險"};

    private static final List<String> types = Arrays.asList(CASE_TYPE_PRIMARY);

    private Long id;
    private String name;
    private String code;
    private Integer type;

    public static int getTypeId(String typeName) { return types.indexOf(typeName); }

    public static List<String> getTypes() {
        return types;
    }

    public boolean isType(String typeName) {
        return isType(getTypeId(typeName));
    }

    public boolean isType(int typeId) {
        return typeId >= 0 && ((0x01<<typeId) & type) != 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CaseTypeObj that = (CaseTypeObj) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
