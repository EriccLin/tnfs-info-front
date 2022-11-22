package com.tnfs.infoApplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnitObj {
    static final Function<UnitObj, Long> UNITOBJ_ID_FUNC = (UnitObj o) -> o == null || o.getId() == null ? Long.MAX_VALUE : o.getId();
    public static final Comparator<UnitObj> UNITOBJ_COMPARATOR = Comparator.comparingLong(UNITOBJ_ID_FUNC::apply);

    private Long id;
    private String name;
    private String engName;
    private UnitObj superviser;
    private String address;
    private Integer level;

    public Long getSuperviserId() {
        return superviser == null ? null : superviser.getId();
    }

    public String getNameWithSuperviser() {
        if (level != null && level <= 1) {
            return name;
        }
        return name + (superviser == null ? "" : "-" +superviser.getName());
    }

    public String getSimpleName() {
        String ret = "";
        String append = "";
        String curName = getName();
        UnitObj cur = this;
        boolean flag = true;
        if (curName != null) {
            if (curName.equals("警政署")) {
                return cur.getName();
            } else {
                if (curName.contains("警察局")) {
                    append = "警察局";
                } else if (curName.contains("分局")) {
                    append = "分局";
                }
            }
        }
        do {
            curName = cur.getName();
            if (curName != null && curName.length() > 0) {
                if (curName.equals("警政署")) {
                    curName = "";
                } else if (curName.contains("警察局")) {
                    int lastIdx = curName.indexOf("政府");
                    if (lastIdx < 0) {
                        lastIdx = curName.indexOf("警察局");
                    }
                    int firstIdx = curName.indexOf("省");
                    firstIdx = firstIdx < 0 ? 0 : firstIdx + 1;
                    curName = curName.substring(firstIdx, lastIdx);
                } else if (curName.contains("分局")) {
                    curName = curName.substring(0, curName.indexOf("分局"));
                } else if (curName.contains("大隊")) {
                    if (curName.contains("交通")) {
                        curName = "交大";
                    } else if (curName.contains("保安")) {
                        curName = "保大";
                    } else if (curName.contains("刑事")) {
                        curName = "刑大";
                    }
                } else if (curName.contains("刑事鑑識中心")) {
                    curName = "鑑識中心";
                } else if (curName.contains("婦幼")) {
                    curName = "婦幼隊";
                } else if (curName.contains("少年")) {
                    curName = "少年隊";
                } else if (curName.contains("派出所") || curName.contains("分駐所") || curName.contains("駐在所")) {
                    curName = curName.substring(0, curName.length() - 3) + "所";
                }
                ret = curName + ret;
            }
            cur = cur.getSuperviser();
            if (cur.getName().contains("臺南市")) {
                break;
            }
        } while (cur != null);
        return ret + append;
    }

    @Override
    public String toString() {
        return getNameWithSuperviser();
    }
}
