package com.tnfs.infoApplication.cache;

import com.sun.istack.internal.NotNull;
import com.tnfs.infoApplication.model.PersonObj;
import com.tnfs.infoApplication.task.PersonLoadingTask;
import com.tnfs.infoApplication.task.PersonSavingTask;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import lombok.Getter;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PersonCache {
    private HashMap<Long, PersonObj> id2Obj = new HashMap<>();
    private HashMap<String, PersonObj> idNumber2Obj = new HashMap<>();

    private static PersonCache instance;

    private byte lock[] = new byte[0];

    private PersonCache() {}

    public static PersonCache getInstance() {
        if (instance == null) {
            synchronized (PersonCache.class) {
                if (instance == null) {
                    instance = new PersonCache();
                }
            }
        }
        return instance;
    }

    public void add(@NotNull PersonObj obj) {
        Long id = obj.getId();
        String idNumber = obj.getIdNumber();
        synchronized (lock) {
            if (id2Obj.containsKey(id)) {
                id2Obj.replace(id, obj);
            } else {
                id2Obj.put(id, obj);
            }
            if (idNumber2Obj.containsKey(idNumber)) {
                idNumber2Obj.replace(idNumber, obj);
            } else {
                idNumber2Obj.put(idNumber, obj);
            }
        }
    }

    public void addAll(List<PersonObj> objs) {
        for(PersonObj obj: objs) {
            add(obj);
        }
    }

    public PersonObj getPersonObj(String idNumber) {
        synchronized (lock) {
            return idNumber2Obj.get(idNumber);
        }
    }

    public PersonObj getPersonObj(Long id) {
        synchronized (lock) {
            return id2Obj.get(id);
        }
    }

    public List<PersonObj> getPersonObjs() {
        synchronized (lock) {
            return new ArrayList<>(id2Obj.values());
        }
    }
}
