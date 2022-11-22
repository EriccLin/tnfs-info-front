package com.tnfs.infoApplication.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tnfs.infoApplication.cache.AuthorizationCache;
import com.tnfs.infoApplication.dto.CriminalCaseDto;
import com.tnfs.infoApplication.dto.PersonDto;
import com.tnfs.infoApplication.error.ApiError;
import com.tnfs.infoApplication.mapper.IPersonObjMapper;
import com.tnfs.infoApplication.model.PersonObj;
import javafx.concurrent.Task;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PersonSavingTask extends BasicStructure<PersonObj> {
    private ObjectMapper objectMapper = new ObjectMapper();
    private static IPersonObjMapper personObjMapper = IPersonObjMapper.INSTANCE;
    private static String path = context_path + "/persons";
    private ApiError apiError = null;
    private int status = 0;
    private PersonObj obj;

    public PersonSavingTask(PersonObj obj) {
        this.obj = obj;
    }

    public int getStatus() { return status; }

    public ApiError getError() { return apiError; }

    @Override
    protected PersonObj call() throws Exception {
        Long id = obj.getId();
        HttpEntityEnclosingRequestBase request = id == null? new HttpPost(url + path) : new HttpPut(url + path);
        String accessToken = AuthorizationCache.getInstance().getAccessToken();
        request.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
        request.setHeader("User-Agent", myversion);

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
                PersonDto dto = gson.fromJson(body, PersonDto.class);
                return personObjMapper.toObj(dto);
            } else {
                apiError = gson.fromJson(body, ApiError.class);
            }
        } catch (IOException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
