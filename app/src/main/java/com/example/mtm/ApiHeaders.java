package com.example.mtm;

import java.util.HashMap;
import java.util.Map;

public class ApiHeaders {
    public static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Cache-Control", "no-cache");
        headers.put("Postman-Token", "<calculated when request is sent>");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Content-Length", "<calculated when request is sent>");
        headers.put("Host", "<calculated when request is sent>");
        headers.put("User-Agent", "PostmanRuntime/7.36.1");
        headers.put("Accept", "*/*");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Connection", "keep-alive");
        return headers;
    }


}
