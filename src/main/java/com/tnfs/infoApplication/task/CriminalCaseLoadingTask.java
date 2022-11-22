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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tnfs.infoApplication.cache.AuthorizationCache;
import com.tnfs.infoApplication.dto.CriminalCaseDto;
import com.tnfs.infoApplication.error.ApiError;
import com.tnfs.infoApplication.mapper.ICriminalCaseObjMapper;
import com.tnfs.infoApplication.model.CriminalCaseObj;
import javafx.concurrent.Task;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CriminalCaseLoadingTask extends BasicStructure<CriminalCaseObj>{
    private ObjectMapper objectMapper = new ObjectMapper();
    private static ICriminalCaseObjMapper criminalCaseObjMapper = ICriminalCaseObjMapper.INSTANCE;
    private static String path = context_path + "/ccases/";
    private Long id = null;
    private ApiError apiError = null;
    private int status = 0;

    public CriminalCaseLoadingTask() {}

    public CriminalCaseLoadingTask(Long id) {
        this.id = id;
    }
    public int getStatus() { return status; }
    public ApiError getError() { return apiError; }

    @Override
    protected CriminalCaseObj call() throws Exception {
        assert id != null;
        HttpGet request = new HttpGet(url + path + id);
        request.setHeader("User-Agent", myversion);
        String accessToken = AuthorizationCache.getInstance().getAccessToken();
        request.setHeader(HttpHeaders.AUTHORIZATION.toString(), accessToken);
        request.setHeader("User-Agent", myversion);

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(request);
        String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        status = response.getStatusLine().getStatusCode();
//        System.out.println("request_url: "+ request.getURI());
//        System.out.println("status: "+ status);
        try {
            client.close();
            if (status == 200) {
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
