package com.tnfs.infoApplication.cache;

import com.tnfs.infoApplication.model.CaseTypeObj;
import com.tnfs.infoApplication.task.CaseTypesLoadingTask;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import lombok.*;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.tnfs.infoApplication.model.CaseTypeObj.CASE_TYPE_PRIMARY;

@Getter
public class CaseTypeCache {

    private static final Comparator<CaseTypeObj> comparator = new Comparator<CaseTypeObj>() {
        @Override
        public int compare(CaseTypeObj o1, CaseTypeObj o2) {
            return o1.getId().compareTo(o2.getId());
        }
    };

    private volatile TreeSet<CaseTypeObj> caseTypeObjs = new TreeSet<>(comparator);
    private static final List<String> types = Arrays.asList(CASE_TYPE_PRIMARY);
    private ImageView imageViewOk = new ImageView(getClass().getResource("../image/ok-64.png").toString());
    private ImageView imageViewCancel = new ImageView(getClass().getResource("../image/cancel-64.png").toString());

    private static CaseTypeCache instance;

    private byte lock[] = new byte[0];

    private CaseTypeCache() {}
    public static CaseTypeCache getInstance() {
        if (instance == null) {
            synchronized (CaseTypeCache.class) {
                if (instance == null) {
                    instance = new CaseTypeCache();
                }
            }
        }
        return instance;
    }

    private void add(CaseTypeObj obj) {
        Long id = obj.getId();
        String name = obj.getName();
        assert id != null;
        caseTypeObjs.add(obj);
    }

    public void addAll(List<CaseTypeObj> objs) {
        synchronized (lock) {
            for(CaseTypeObj obj: objs) {
                add(obj);
            }
        }
    }

    public CaseTypeObj getCaseTypeObj(String name) {
        synchronized (lock) {
            Optional<CaseTypeObj> optionalCaseTypeObj = caseTypeObjs.stream().filter(obj -> name.equals(obj.getName())).findAny();
            return optionalCaseTypeObj.isPresent() ? optionalCaseTypeObj.get() : null;
        }
    }

    public List<CaseTypeObj> getCaseTypes() {
        synchronized (lock) {
            return caseTypeObjs.stream().collect(Collectors.toList());
        }
    }

    public int getSize() { return caseTypeObjs.size(); }
}
