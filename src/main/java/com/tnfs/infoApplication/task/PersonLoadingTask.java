package com.tnfs.infoApplication.task;

import com.google.gson.reflect.TypeToken;
import com.tnfs.infoApplication.cache.AuthorizationCache;
import com.tnfs.infoApplication.dto.PersonDto;
import com.tnfs.infoApplication.error.ApiError;
import com.tnfs.infoApplication.mapper.IPersonObjMapper;
import com.tnfs.infoApplication.model.PersonObj;
import javafx.concurrent.Task;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PersonLoadingTask extends BasicStructure<List<PersonObj>> {
    private static IPersonObjMapper personObjMapper = IPersonObjMapper.INSTANCE;
    private static String path = context_path + "/persons";
    private String append;
    private ApiError apiError = null;
    public enum TYPE{
        WITH_PERSON_ID,
        WITH_PERSON_ID_NUMBER,
        WITH_CRIMINAL_CASE_ID,
    };
    TYPE loadType = TYPE.WITH_PERSON_ID;
    private int status = 0;

    public PersonLoadingTask() {}

    public void usePersonId(Long id) {
        this.append = "/"+id;
        loadType = TYPE.WITH_PERSON_ID;
    }

    public void usePersonIdNumber(String idnumber) {
        this.append = "/idn/"+idnumber;
        loadType = TYPE.WITH_PERSON_ID_NUMBER;
    }

    public void useCriminalCaseId(Long id) {
        this.append = "/criminalCase/"+id;
        loadType = TYPE.WITH_CRIMINAL_CASE_ID;
    }

    public int getStatus() { return status; }

    public ApiError getError() { return apiError; }

    @Override
    protected List<PersonObj> call() throws Exception {
        HttpGet request = new HttpGet(url + path + append);
        String accessToken = AuthorizationCache.getInstance().getAccessToken();
        request.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
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
                List<PersonDto> dtos = new ArrayList<>();
                if (loadType == TYPE.WITH_CRIMINAL_CASE_ID) {
                    dtos = gson.fromJson(body, new TypeToken<List<PersonDto>>(){}.getType());
                } else {
                    PersonDto dto = gson.fromJson(body, PersonDto.class);
                    dtos.add(dto);
                }
                return personObjMapper.INSTANCE.toObj(dtos);
            } else {
                apiError = gson.fromJson(body, ApiError.class);
            }
        } catch (IOException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
