package com.medyatakip.app.request;

import com.google.gson.annotations.SerializedName;

public class TokenRequest {

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("grant_type")
    private String grantType;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public TokenRequest(String clientId, String clientSecret, String grantType, String username, String password) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.username = username;
        this.password = password;
    }
}
