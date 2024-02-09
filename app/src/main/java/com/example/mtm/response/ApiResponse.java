package com.example.mtm.response;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("response")
    private ResponseData responseData;

    public ResponseData getResponseData() {
        return responseData;
    }

    public class ResponseData {
        @SerializedName("errorMessage")
        private String errorMessage;

        @SerializedName("errorCode")
        private String errorCode;

        @SerializedName("status")
        private boolean status;

        public String getErrorMessage() {
            return errorMessage;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public boolean isStatus() {
            return status;
        }
    }

}

