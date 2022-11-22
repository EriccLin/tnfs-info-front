package com.tnfs.infoApplication.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.tnfs.infoApplication.cache.CommonCache;
import com.tnfs.infoApplication.model.UnitObj;
import com.tnfs.infoApplication.util.LocalDateAdapter;
import com.tnfs.infoApplication.util.LocalDateTimeAdapter;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class BasicStructure<T> extends Task<T> {
    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter().nullSafe())
            .create();
    public static String url = "http://127.0.0.1:8080";
    public static String context_path = "/api-v1";
    public static String myversion = "TNFS_CLIENT_APPLICATION/v1.0";

    void work(EventHandler<WorkerStateEvent> sucess_val, EventHandler<WorkerStateEvent> failed_val) {
        Notifications notifications = Notifications.create()
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> {});
        if (sucess_val != null) {
            this.setOnSucceeded(sucess_val);
        }
        if (failed_val != null) {
            this.setOnFailed(failed_val);
        } else {
            this.setOnFailed(e -> {
                notifications.text("連線失敗").graphic(CommonCache.getInstance().getViewCancel()).show();
            });
        }
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }
}
