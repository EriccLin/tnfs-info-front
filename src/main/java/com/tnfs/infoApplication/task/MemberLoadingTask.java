package com.tnfs.infoApplication.task;

import com.google.gson.reflect.TypeToken;
import com.tnfs.infoApplication.cache.AuthorizationCache;
import com.tnfs.infoApplication.dto.MemberDto;
import com.tnfs.infoApplication.error.ApiError;
import com.tnfs.infoApplication.mapper.IMemberObjMapper;
import com.tnfs.infoApplication.model.MemberObj;
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

public class MemberLoadingTask extends BasicStructure<List<MemberObj>> {
    private static final IMemberObjMapper memberObjMapper = IMemberObjMapper.INSTANCE;
    private static final String path = context_path + "/members";
    private String append;
    private ApiError apiError = null;
    private int status = 0;
    public enum TYPE{
        WITH_MEMBER_ID,
        WITH_UNIT_ID,
    }
    TYPE loadType = TYPE.WITH_MEMBER_ID;

    public MemberLoadingTask() {}

    public void withMemberId(Long id) {
        this.append = "/"+id;
        loadType = TYPE.WITH_MEMBER_ID;
    }

    public void withUnitId(Long id) {
        this.append = "/units/"+id;
        loadType = TYPE.WITH_UNIT_ID;
    }

    public int getStatus() { return status; }

    public ApiError getError() { return apiError; }

    @Override
    protected List<MemberObj> call() throws Exception {
        HttpGet request = new HttpGet(url + path + append);
        String accessToken = AuthorizationCache.getInstance().getAccessToken();
        request.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
        request.setHeader("User-Agent", myversion);
        List<MemberObj> ret = null;
        try (CloseableHttpClient client = HttpClients.createDefault()){
            CloseableHttpResponse response = client.execute(request);
            String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            status = response.getStatusLine().getStatusCode();
//            System.out.println("request_url: "+ request.getURI());
//            System.out.println("status: "+ status);

//            System.out.println("body: "+body);
            if (status == HttpStatus.SC_OK) { // 200
                List<MemberDto> dtos = new ArrayList<>();
                if (loadType == TYPE.WITH_MEMBER_ID) {
                    MemberDto dto = gson.fromJson(body, MemberDto.class);
                    dtos.add(dto);
                } else {
                    dtos = gson.fromJson(body, new TypeToken<List<MemberDto>>(){}.getType());
                }
                ret = memberObjMapper.toObj(dtos);
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
}
