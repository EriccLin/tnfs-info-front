package com.tnfs.infoApplication.cache;

import com.sun.istack.internal.NotNull;
import com.tnfs.infoApplication.model.UnitObj;

import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.tnfs.infoApplication.model.UnitObj.UNITOBJ_COMPARATOR;

@Getter
public class UnitCache {
    private volatile HashMap<Long, UnitObj> id2Obj = new HashMap<>();
    private volatile HashMap<String, TreeSet<UnitObj>> name2Objs = new HashMap<>();
    private volatile HashMap<Long, TreeSet<UnitObj>> id2SuperviseeObjs = new HashMap<>();

    private static UnitCache instance;

    private byte lock[] = new byte[0];

    private UnitCache() {}

    public static UnitCache getInstance() {
        if (instance == null) {
            synchronized (UnitCache.class) {
                if (instance == null) {
                    instance = new UnitCache();
                }
            }
        }
        return instance;
    }

    public void add(@NotNull UnitObj obj) {
        if (obj == null) {
            return;
        }
        Long id = obj.getId();
        String name = obj.getName();
        Long superviserId = obj.getSuperviserId();
        if (id == null || name == null) {
            return;
        }
        synchronized (lock) {
//            if (superviserId != null) {
//                obj.setSuperviser(id2Obj.get(superviserId));
//            }

            UnitObj old = id2Obj.get(id);
            UnitObj oldSuperviser = old != null ? old.getSuperviser() : null;
            Long oldSuperviserId = oldSuperviser != null ? oldSuperviser.getId() : null;
            // current->parent ?= old->parent , Discussion!!!
            // (cp:current->parent, op:old->parent)
            // type-1: ( null, null): no-op
            // type-2: ( null,exist): remove relation from old->parent to old
            // type-3: (exist, null): add relation from current->parent to current
            // type-4: (exist,exist):
            //      type-4a: current->parent.id = old->parent.id: because we believe the old parent is better
            //              remove relation from old->parent to old
            //              add relation from old->parent to current
            //      type-4b: current->parent.id!= old->parent.id:
            //              remove relation from old->parent to old
            //              add relation from current->parent to current
            // type-X: Special Case: if old doesn't exist and obj with id(current->parent) exists
            //              add relation from obj to current
            //              add relation from current to obj
            if (oldSuperviserId != null) {
                TreeSet<UnitObj> set = id2SuperviseeObjs.get(oldSuperviserId);
                if (set == null) {
                    System.out.println("TreeSet<UnitObj> set 發生錯誤!!");
                }
                set.remove(old);  // type-2, type-4
                if (superviserId.equals(oldSuperviserId)) { // type-4a
                    set.add(obj); // type-4a
                    obj.setSuperviser(oldSuperviser); // because we believe the old parent is better
                }
            }
            if (superviserId != null) { // type-3, type-4b ---or--- type-X: if oldSuperviserId is null, add relation
                if (!superviserId.equals(oldSuperviserId) || oldSuperviserId == null) {
                    TreeSet<UnitObj> set = id2SuperviseeObjs.get(superviserId);
                    if (set == null) {
                        set = new TreeSet<UnitObj>(UNITOBJ_COMPARATOR);
                        set.add(obj);
                        id2SuperviseeObjs.put(superviserId, set);
                    } else {
                        set.add(obj);
                    }
                    if (oldSuperviserId == null) { // type-X: if oldSuperviserId is null, add relation
                        UnitObj superviser = id2Obj.get(superviserId);
                        obj.setSuperviser(superviser);
                    }
                }
            }
            // update current if exists or create one
            id2Obj.put(id, obj);
            if (superviserId != null && !id2Obj.containsKey(superviserId)) {
                id2Obj.put(superviserId, obj.getSuperviser());
            }

            if (!name2Objs.containsKey(name)) {
                name2Objs.put(name, new TreeSet<UnitObj>(UNITOBJ_COMPARATOR));
            }
            name2Objs.get(name).add(obj);
            // get children from our maintained DS. for each child, child->parent = current
            TreeSet<UnitObj> supervisees = id2SuperviseeObjs.get(id);
            if (supervisees != null) {
                supervisees.forEach(supervisee -> supervisee.setSuperviser(obj));
            }
        }
    }

    public void addAll(List<UnitObj> objs) {
        for (UnitObj obj : objs) {
            add(obj);
        }
    }

    public UnitObj getUnitObj(Long id) {
        synchronized (lock) {
            return id2Obj.get(id);
        }
    }

    public List<UnitObj> getUnitObjs(String name) {
        synchronized (lock) {
            return name2Objs.containsKey(name) ? new ArrayList<>(name2Objs.get(name)) : null;
        }
    }

    public List<UnitObj> getSuperviseeUnitObjs(Long parentId) {
        synchronized (lock) {
            return id2SuperviseeObjs.containsKey(parentId) ? new ArrayList<>(id2SuperviseeObjs.get(parentId)) : null;
        }
    }

    public List<UnitObj> getUnitObjs() {
        synchronized (lock) { // level < 0 is sentinel
            return id2Obj.values().stream().filter(obj -> obj.getLevel() >= 0).collect(Collectors.toList());
        }
    }
}
