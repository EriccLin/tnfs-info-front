package com.tnfs.infoApplication.cache;

import com.tnfs.infoApplication.model.AccountObj;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class AuthorizationCache {
    private AccountObj accountObj;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime start;
    private int duration;
    private static final String bearer = "Bearer ";
    private static AuthorizationCache instance;
    private AuthorizationCache() {}
    public static AuthorizationCache getInstance() {
        if (instance == null) {
            synchronized (AuthorizationCache.class) {
                if (instance == null) {
                    instance = new AuthorizationCache();
                }
            }
        }
        return instance;
    }

    public void setConnInfo(Map<String, String> connInfo) {
        assert connInfo != null && connInfo.size() == 4;
        accessToken = connInfo.get("access_token");
        refreshToken = connInfo.get("refresh_token");
        start = LocalDateTime.parse(connInfo.get("start"));
        duration = Integer.parseInt(connInfo.get("duration"));
        System.out.println(accessToken);
    }

    public void setAccountObj(AccountObj obj) {
        accountObj = obj;
    }

    public String getAccessToken() { return bearer + accessToken; }

    public String getRefeshToken() { return bearer + refreshToken; }

    public LocalDateTime getStart() { return start; }

    public LocalDateTime getEnd() { return start.plusSeconds(duration/1000); }

    @Override
    public String toString() {
        return "AuthorizationCache{" +
                "accountObj=" + accountObj +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", start=" + start +
                ", duration(ms)=" + duration +
                '}';
    }
}
