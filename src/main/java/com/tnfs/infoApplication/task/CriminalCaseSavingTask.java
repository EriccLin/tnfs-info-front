package com.tnfs.infoApplication.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tnfs.infoApplication.cache.AuthorizationCache;
import com.tnfs.infoApplication.dto.CriminalCaseDto;
import com.tnfs.infoApplication.error.ApiError;
import com.tnfs.infoApplication.mapper.ICriminalCaseObjMapper;
import com.tnfs.infoApplication.model.CriminalCaseObj;
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

public class CriminalCaseSavingTask extends BasicStructure<CriminalCaseObj> {
    private ObjectMapper objectMapper = new ObjectMapper();
    private static ICriminalCaseObjMapper criminalCaseObjMapper = ICriminalCaseObjMapper.INSTANCE;
    private static String path = context_path + "/ccases";
    private ApiError apiError = null;
    private int status = 0;
    private CriminalCaseObj obj;

    public CriminalCaseSavingTask(CriminalCaseObj criminalCaseObj) {
        this.obj = criminalCaseObj;
    }

    public int getStatus() { return status; }

    public ApiError getError() { return apiError; }
    @Override
    protected CriminalCaseObj call() throws Exception {
        assert obj != null;
        Long criminalCaseId = obj.getId();
        HttpEntityEnclosingRequestBase request = criminalCaseId == null? new HttpPost(url + path) : new HttpPut(url + path);
        String accessToken = AuthorizationCache.getInstance().getAccessToken();
        request.setHeader(HttpHeaders.AUTHORIZATION.toString(), accessToken);
        request.setHeader("User-Agent", myversion);
//        request.setHeader("Content-type", "application/json");
//        request.setHeader("Accept", "application/json");
        StringEntity entity = new StringEntity(gson.toJson(obj), StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        try (CloseableHttpClient client = HttpClients.createDefault()){
            CloseableHttpResponse response = client.execute(request);
            String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            status = response.getStatusLine().getStatusCode();
            System.out.println("request_url: "+ request.getURI());
            System.out.println("status: "+ status);

            System.out.println("body: "+body);
            if (status == HttpStatus.SC_ACCEPTED) { // 202
                CriminalCaseDto dto = gson.fromJson(body, CriminalCaseDto.class);
                return ICriminalCaseObjMapper.INSTANCE.toObj(dto);
            } else {
                apiError = gson.fromJson(body, ApiError.class);
            }
        } catch (IOException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
