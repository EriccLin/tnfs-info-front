package com.tnfs.infoApplication.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import com.tnfs.infoApplication.cache.AuthorizationCache;
import com.tnfs.infoApplication.dto.UnitDto;
import com.tnfs.infoApplication.error.ApiError;
import com.tnfs.infoApplication.mapper.IUnitObjMapper;
import com.tnfs.infoApplication.model.UnitObj;
import javafx.concurrent.Task;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class UnitSavingTask extends BasicStructure<UnitObj> {
    private ObjectMapper objectMapper = new ObjectMapper();
    private static IUnitObjMapper unitObjMapper = IUnitObjMapper.INSTANCE;
    private static String path = context_path + "/units";
    private ApiError apiError = null;
    private int status = 0;
    private UnitObj obj;

    public UnitSavingTask(UnitObj obj) {
        this.obj = obj;
    }

    public int getStatus() { return status; }

    public ApiError getError() { return apiError; }

    private boolean checkNameWithSuperviserIdIsExists(String name, Long superviserId) {
        HttpGet httpGet = new HttpGet(url+path+"/name/"+obj.getName());
        String accessToken = AuthorizationCache.getInstance().getAccessToken();
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
        httpGet.setHeader("User-Agent", myversion);
        boolean ret = false;

        try (CloseableHttpClient client = HttpClients.createDefault()){
            CloseableHttpResponse response = client.execute(httpGet);
            String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            status = response.getStatusLine().getStatusCode();

            if (status == HttpStatus.SC_OK) { // 200
                List<UnitDto> dtos = gson.fromJson(body, new TypeToken<List<UnitDto>>(){}.getType());
                ret = superviserId == null ?
                        dtos.stream().anyMatch(dto -> name.equals(dto.getName()) && dto.getSuperviserId() == null) :
                        dtos.stream().anyMatch(dto -> name.equals(dto.getName()) && superviserId.equals(dto.getSuperviserId()));
            }
        } catch (IOException | IllegalArgumentException ex) {
            ex.printStackTrace();
        } finally {
            return ret;
        }
    }

    @Override
    protected UnitObj call() throws Exception {
        UnitObj ret = null;
        Long id = obj.getId();
        HttpEntityEnclosingRequestBase request = id != null ? new HttpPut(url + path) : new HttpPost(url + path);
        String accessToken = AuthorizationCache.getInstance().getAccessToken();
        request.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
        request.setHeader("User-Agent", myversion);

        StringEntity entity = new StringEntity(gson.toJson(obj), StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)){
            String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            status = response.getStatusLine().getStatusCode();

            if (status == HttpStatus.SC_ACCEPTED) { // 202
                UnitDto dto = gson.fromJson(body, UnitDto.class);
                ret = unitObjMapper.toObj(dto);
            } else {
                apiError = gson.fromJson(body, ApiError.class);
            }
        } catch (IOException | IllegalArgumentException ex) {
            ex.printStackTrace();
        } finally {
//            updateValue(ret);
            return ret;
        }
    }
}
