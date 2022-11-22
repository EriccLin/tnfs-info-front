package com.tnfs.infoApplication.cache;

import com.tnfs.infoApplication.model.InChargeObj;
import com.tnfs.infoApplication.model.InChargerObj;
import com.tnfs.infoApplication.model.UnitObj;
import com.tnfs.infoApplication.task.UnitLoadingTask;
import javafx.geometry.Pos;
import javafx.util.Duration;
import lombok.Getter;
import org.controlsfx.control.Notifications;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class InChargeCache {
    static final Comparator<InChargeObj> IN_CHARGE_OBJ_COMPARATOR = (o1, o2) -> {
        final Function<InChargeObj, Long> IN_CHARGE_OBJ_CID_FUNC = o -> o == null ? Long.MAX_VALUE : o.getCriminalCaseId() == null ? Long.MAX_VALUE : o.getCriminalCaseId();
        final Function<InChargeObj, Long> IN_CHARGE_OBJ_UID_FUNC = o -> o == null ? Long.MAX_VALUE : o.getUnitId() == null ? Long.MAX_VALUE : o.getUnitId();
        Long cid1 = IN_CHARGE_OBJ_CID_FUNC.apply(o1), cid2 = IN_CHARGE_OBJ_CID_FUNC.apply(o2);
        Long uid1 = IN_CHARGE_OBJ_UID_FUNC.apply(o1), uid2 = IN_CHARGE_OBJ_UID_FUNC.apply(o2);
        return cid1.equals(cid2) ? Long.compare(uid1, uid2) : Long.compare(cid1, cid2);
    };

    private volatile HashMap<Long, HashMap<Long, InChargeObj>> cid2Objs = new HashMap<>();

    private static InChargeCache instance;

    private byte lock[] = new byte[0];

    private InChargeCache() {}

    public static InChargeCache getInstance() {
        if (instance == null) {
            synchronized (InChargeCache.class) {
                if (instance == null) {
                    instance = new InChargeCache();
                }
            }
        }
        return instance;
    }

    public void add(InChargerObj obj) {

    }

    public void add(InChargeObj obj) {
        if (obj == null) { return; }
        Long criminalCaseId = obj.getCriminalCaseId();
        Long unitId = obj.getUnitId();
        if (criminalCaseId == null || unitId == null) { return; }

        if (UnitCache.getInstance().getUnitObj(unitId) == null) {
            startUnitLoadingWorkerWithGettingAncestorAndSiblingMode(unitId);
        }

        synchronized (lock) {
            if (cid2Objs.containsKey(criminalCaseId)) {
                cid2Objs.get(criminalCaseId).put(unitId, obj);
            } else {
                HashMap<Long, InChargeObj> map = new HashMap<>();
                map.put(unitId, obj);
                cid2Objs.put(criminalCaseId, map);
            }
            try {
                wait(500L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addAll(List<InChargeObj> objs) {
        for (InChargeObj obj : objs) {
            add(obj);
        }
    }

    public InChargeObj getInChargeObj(Long criminalCaseId, Long unitId) {
        synchronized (lock) {
            HashMap<Long, InChargeObj> map = cid2Objs.get(criminalCaseId);
            if (map == null) {
                return null;
            }
            Optional<InChargeObj> ret = map.entrySet().stream().filter(entry -> unitId.equals(entry.getKey())).map(Map.Entry::getValue).findFirst();
            return ret.isPresent() ? ret.get() : null;
        }
    }

    public List<InChargeObj> getInChargeObjs(Long criminalCaseId) {
        synchronized (lock) {
            return cid2Objs.containsKey(criminalCaseId) ? cid2Objs.get(criminalCaseId).values().stream().collect(Collectors.toList()) : null;
        }
    }

    public void startUnitLoadingWorkerWithGettingAncestorAndSiblingMode(Long id) {
        UnitLoadingTask unitLoadingTask = new UnitLoadingTask();
        unitLoadingTask.withAncestorAndSiblingMode(id);
        startUnitLoadingWorker(unitLoadingTask);
        unitLoadingTask.setOnSucceeded(e-> {
            int status = unitLoadingTask.getStatus();
            if (status >= 200 && status < 300) {
                UnitCache.getInstance().addAll(unitLoadingTask.getValue());
            }
            notifyAll();
        });
    }

    protected void startUnitLoadingWorker(UnitLoadingTask unitLoadingTask) {
        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {});
        unitLoadingTask.setOnFailed(e -> {
            System.out.println("連線失敗 in " + getClass().getName());
//            notifications.text("連線失敗").graphic(imageViewCancel).show();
            notifyAll();
        });
        Thread thread = new Thread(unitLoadingTask);
        thread.setDaemon(true);
        thread.start();
    }
}
