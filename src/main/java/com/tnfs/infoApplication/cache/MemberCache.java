package com.tnfs.infoApplication.cache;

import com.tnfs.infoApplication.model.MemberObj;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.tnfs.infoApplication.model.MemberObj.MEMBEROBJ_RANK_FUNC;

@Getter
public class MemberCache {
//    static final Function<MemberObj, Long> MEMBEROBJ_ID_FUNC = (MemberObj o) -> o == null || o.getId() == null ? Long.MAX_VALUE : (MEMBEROBJ_RANK_FUNC.apply(o.getMrank()) << 22 )+ (o.getUnitLevel() << 11) + o.get;
//    static final Comparator<MemberObj> MEMBEROBJ_COMPARATOR = Comparator.comparingLong(MEMBEROBJ_ID_FUNC::apply);

    private Comparator<MemberObj> comparator = new Comparator<MemberObj>() {
        @Override
        public int compare(MemberObj o1, MemberObj o2) {
            Long u1 = (o1.getUnitLevel() == null ? Long.MAX_VALUE : o1.getUnitLevel() << 11) + (o1.getUnitSuperviserId() == null ? 0L : o1.getUnitSuperviserId());
            Long u2 = (o2.getUnitLevel() == null ? Long.MAX_VALUE : o2.getUnitLevel() << 11) + (o2.getUnitSuperviserId() == null ? 0L : o2.getUnitSuperviserId());
            Long m1 = MEMBEROBJ_RANK_FUNC.apply(o1.getMrank());
            Long m2 = MEMBEROBJ_RANK_FUNC.apply(o2.getMrank());
            return u1.equals(u2) ?
                    (m1.equals(m2) ?  (o1.getPersonName().equals(o2.getPersonName()) ?
                            o1.getId().compareTo(o2.getId()) : o1.getPersonName().compareTo(o2.getPersonName())) : m1.compareTo(m2)) :
                            u1.compareTo(u2);
        }
    };

    private volatile HashMap<Long, MemberObj> id2Obj = new HashMap<>();
    private volatile HashMap<String, TreeSet<MemberObj>> name2Objs = new HashMap<>();

    private static MemberCache instance;

    private byte lock[] = new byte[0];

    private MemberCache() {}

    public static MemberCache getInstance() {
        if (instance == null) {
            synchronized (MemberCache.class) {
                if (instance == null) {
                    instance = new MemberCache();
                }
            }
        }
        return instance;
    }

    public void add(MemberObj obj) {
        if (obj == null || obj.getId() == null) {
            return;
        }
        synchronized (lock) {
            Long id = obj.getId();
            String name = obj.getPersonName();
            id2Obj.put(id, obj);

            if (!name2Objs.containsKey(name)) {
                name2Objs.put(name, new TreeSet<MemberObj>(comparator));
            }
            name2Objs.get(name).add(obj);
        }
    }

    public void addAll(List<MemberObj> objs) {
        for (MemberObj obj : objs) {
            add(obj);
        }
    }

    public MemberObj getMemberObj(Long id) {
        synchronized (lock) {
            return id2Obj.get(id);
        }
    }

    public List<MemberObj> getMemberObjs() {
        synchronized (lock) {
            return id2Obj.values().stream().collect(Collectors.toList());
        }
    }

    public List<MemberObj> getMemberObjs(String name) {
        synchronized (lock) {
            return name2Objs.containsKey(name) ? name2Objs.get(name).stream().collect(Collectors.toList()) : null;
        }
    }
}
