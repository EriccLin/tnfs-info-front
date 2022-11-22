/**
 * Thread--Worker--Task
 * ref: https://skeoop.github.io/javafx/WorkerThreads.html
 * ref: http://www2.lawrence.edu/fast/GREGGJ/CMSC250/HttpClient/Registration.html?fbclid=IwAR3i_MuMsv2HLRryLrrBNOBvQyNnY-fM7UQQfbkO_taAThiBQUPjDdvafwQ
 *
 * Request URL Using Apache HttpClient
 * ref: https://stackoverflow.com/questions/14024625/how-to-get-httpclient-returning-status-code-and-response-body/26285342?fbclid=IwAR2rYtD0hEZTXfFcQf-j3Y85TXnbR81wG61KHHljXMDh66EiFz3DRwyAnLQ
 *
 */

package com.tnfs.infoApplication.task;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import com.tnfs.infoApplication.cache.AuthorizationCache;
import com.tnfs.infoApplication.dto.AccountDto;
import com.tnfs.infoApplication.error.ApiError;
import com.tnfs.infoApplication.mapper.IAccountObjMapper;
import com.tnfs.infoApplication.model.AccountObj;
import javafx.concurrent.Task;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginValidationTask extends BasicStructure<AccountObj>{
    private ObjectMapper objectMapper = new ObjectMapper();
    private static IAccountObjMapper accountObjMapper = IAccountObjMapper.INSTANCE;;
    private static String path = context_path + "/login";
    private static String pathLoadAccount = context_path + "/accounts/name/";
    private String username = null;
    private String password = null;
    private ApiError apiError = null;
    private int status = 0;

    public LoginValidationTask(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public int getStatus() { return status; }
    public ApiError getError() { return apiError; }

    @Override
    protected AccountObj call() throws Exception {
        HttpPost httpPost = new HttpPost(url + path);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
//        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        httpPost.setHeader("User-Agent", myversion);
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(httpPost);
        String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        status = response.getStatusLine().getStatusCode();
        if (status == 200) {
            Map<String, String> connInfo = gson.fromJson(body, new TypeToken<Map<String, String>>(){}.getType());
            AuthorizationCache.getInstance().setConnInfo(connInfo);
            httpPost = new HttpPost(url + pathLoadAccount + username);
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, AuthorizationCache.getInstance().getAccessToken());
            response = client.execute(httpPost);
            status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                AccountDto dto = gson.fromJson(body, AccountDto.class);
                AccountObj accountObj = IAccountObjMapper.INSTANCE.toObj(dto);
                return accountObj;
            }
        }
        apiError = gson.fromJson(body, ApiError.class);
        return null;
    }
}
