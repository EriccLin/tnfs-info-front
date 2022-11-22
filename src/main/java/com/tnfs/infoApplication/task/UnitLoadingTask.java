package com.tnfs.infoApplication.task;

import com.google.gson.reflect.TypeToken;
import com.tnfs.infoApplication.cache.AuthorizationCache;
import com.tnfs.infoApplication.cache.CommonCache;
import com.tnfs.infoApplication.dto.UnitDto;
import com.tnfs.infoApplication.error.ApiError;
import com.tnfs.infoApplication.mapper.IUnitObjMapper;
import com.tnfs.infoApplication.model.UnitObj;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UnitLoadingTask extends BasicStructure<List<UnitObj>> {
    private static final IUnitObjMapper unitObjMapper = IUnitObjMapper.INSTANCE;
    private static final String path = context_path + "/units";
    private String append;
    private ApiError apiError = null;
    private int status = 0;
    public enum TYPE{
        WITH_UNIT_ID,
        WITH_SUPERVISER_ID,
        WITH_ANCESTOR_ID,
        WITH_ANCESTOR_AND_SIBLING_MODE,
        WITH_UNIT_NAME,
    }
    TYPE loadType = TYPE.WITH_UNIT_ID;

    public UnitLoadingTask() {}

    public void withUnitId(Long id) {
        this.append = "/"+id;
        loadType = TYPE.WITH_UNIT_ID;
    }

    public void withSupervierId(Long id) {
        this.append = "/superviser/"+id;
        loadType = TYPE.WITH_SUPERVISER_ID;
    }

    public void withAncestorId(Long id) {
        this.append = "/ancestor/"+id;
        loadType = TYPE.WITH_ANCESTOR_ID;
    }

    public void withAncestorAndSiblingMode(Long id) {
        this.append = "/ancestorAndSibling/"+id;
        loadType = TYPE.WITH_ANCESTOR_AND_SIBLING_MODE;
    }

    public void withPartOfName(String name) {
        this.append = "/partOfName/"+name;
        loadType = TYPE.WITH_UNIT_NAME;
    }

    public int getStatus() { return status; }

    public ApiError getError() { return apiError; }

    @Override
    protected List<UnitObj> call() throws Exception {
        HttpGet request = new HttpGet(url + path + append);
        String accessToken = AuthorizationCache.getInstance().getAccessToken();
        request.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
        request.setHeader("User-Agent", myversion);
        List<UnitObj> ret = null;
        try (CloseableHttpClient client = HttpClients.createDefault()){
            CloseableHttpResponse response = client.execute(request);
            String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            status = response.getStatusLine().getStatusCode();
//            System.out.println("request_url: "+ request.getURI());
//            System.out.println("status: "+ status);

//            System.out.println("body: "+body);
            if (status == HttpStatus.SC_OK) { // 200
                List<UnitDto> dtos = new ArrayList<>();
                if (loadType == TYPE.WITH_UNIT_ID) {
                    UnitDto dto = gson.fromJson(body, UnitDto.class);
                    dtos.add(dto);
                } else {
                    dtos = gson.fromJson(body, new TypeToken<List<UnitDto>>(){}.getType());
                }
                ret = unitObjMapper.toObj(dtos);
            } else {
                apiError = gson.fromJson(body, ApiError.class);
            }
        } catch (IOException | IllegalArgumentException ex) {
            ex.printStackTrace();
        } finally {
//            if (isCancelled()) {
//                apiError.setMessage("線程被迫取消");
//                ret = null;
//            }
//            updateValue(ret);
            return ret;
        }
    }

    @Override
    public void work(EventHandler<WorkerStateEvent> sucess_val, EventHandler<WorkerStateEvent> failed_val) {
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
