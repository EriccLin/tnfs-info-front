package com.tnfs.infoApplication.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import com.tnfs.infoApplication.cache.AuthorizationCache;
import com.tnfs.infoApplication.dto.CaseTypeDto;
import com.tnfs.infoApplication.dto.CriminalCaseDto;
import com.tnfs.infoApplication.error.ApiError;
import com.tnfs.infoApplication.mapper.ICaseTypeObjMapper;
import com.tnfs.infoApplication.mapper.ICriminalCaseObjMapper;
import com.tnfs.infoApplication.model.CaseTypeObj;
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

public class CaseTypesLoadingTask extends BasicStructure<List<CaseTypeObj>> {
    private static ICaseTypeObjMapper caseTypeObjMapper = ICaseTypeObjMapper.INSTANCE;
    private static String path = context_path + "/caseTypes";
    private Long id = null;
    private ApiError apiError = null;
    private int status = 0;

    public CaseTypesLoadingTask() {}

    public CaseTypesLoadingTask(Long id) {this.id = id;}

    public int getStatus() { return status; }

    public ApiError getError() { return apiError; }

    @Override
    protected List<CaseTypeObj> call() throws Exception {
        HttpGet request = id == null ? new HttpGet(url + path): new HttpGet(url + path +"/"+id);
        String accessToken = AuthorizationCache.getInstance().getAccessToken();
        request.setHeader(HttpHeaders.AUTHORIZATION.toString(), accessToken);
        request.setHeader("User-Agent", myversion);

        try (CloseableHttpClient client = HttpClients.createDefault()){
            CloseableHttpResponse response = client.execute(request);
            String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            status = response.getStatusLine().getStatusCode();
//            System.out.println("request_url: "+ request.getURI());
//            System.out.println("status: "+ status);
//
//            System.out.println("body: "+body);
            if (status == HttpStatus.SC_OK) { // 200
                List<CaseTypeDto> dtos = gson.fromJson(body, new TypeToken<List<CaseTypeDto>>(){}.getType());
                return caseTypeObjMapper.toObj(dtos);
            } else {
                apiError = gson.fromJson(body, ApiError.class);
            }
        } catch (IOException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
