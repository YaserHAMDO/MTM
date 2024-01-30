package com.example.mtm;

public class Headers2 {

    private String cacheControl;
    private String postmanToken;
    private String contentType;
    private String contentLength;
    private String host;
    private String userAgent;
    private String accept;
    private String acceptEncoding;
    private String connection;


    public Headers2(String cacheControl, String postmanToken, String contentType, String contentLength, String host, String userAgent, String accept, String acceptEncoding, String connection) {
        this.cacheControl = cacheControl;
        this.postmanToken = postmanToken;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.host = host;
        this.userAgent = userAgent;
        this.accept = accept;
        this.acceptEncoding = acceptEncoding;
        this.connection = connection;
    }
}
