package com.example.mtm.response;

import com.google.gson.annotations.SerializedName;

public class MarkAsReadResponse {
    @SerializedName("data")
    private boolean success;

    @SerializedName("response")
    private Object responseData; // Change the type according to the actual response data type

    public boolean isSuccess() {
        return success;
    }

    public Object getResponseData() {
        return responseData;
    }
}
