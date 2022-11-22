package com.tnfs.infoApplication.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ParameterStringBuilder {
    public static String getParameterString(Map<String, String> params) throws UnsupportedEncodingException {
        String ret = "";
        for (Map.Entry<String, String> entry: params.entrySet()) {
            ret = ret + URLEncoder.encode(entry.getKey(), "UTF-8") + "=" +
                    URLEncoder.encode(entry.getValue(), "UTF-8") + "&";
        }
        int len = ret.length();
        return len > 0 ? ret.substring(0,len-1) : "";
    }
}
