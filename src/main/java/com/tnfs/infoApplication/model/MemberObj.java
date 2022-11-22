package com.tnfs.infoApplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberObj {
    private static String MEMBER_RANK_COLLECTION = "主任技正專員股長隊長警務正警務員副隊長分隊長巡官小隊長偵查佐警務佐巡佐警員";

    public static String[] MEMBER_RANKS = {"警員","巡佐","警務佐","偵查佐","小隊長","巡官","分隊長","副隊長","警務員","警務正","隊長","股長","技正","專員","主任"};

    static public final Function<String, Long> MEMBEROBJ_RANK_FUNC = (String s) -> s == null ? Long.MAX_VALUE : MEMBER_RANK_COLLECTION.indexOf(s);


    private Long id;
    private LocalDate sdate;
    private LocalDate edate;
    private String mrank;
    private Long personId;
    private String personName;
    private Long unitId;
    private String unitName;
    private Long unitSuperviserId;
    private String unitSuperviserName;
    private Integer unitLevel;

    public String getNameWithMrankAndUnit() {
        return String.format("%s%s(%s)", mrank, personName, unitName);
    }

    @Override
    public String toString() { return getNameWithMrankAndUnit(); }
}
