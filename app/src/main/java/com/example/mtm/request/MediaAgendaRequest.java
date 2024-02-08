package com.example.mtm.request;

import com.google.gson.annotations.SerializedName;

public class MediaAgendaRequest {

    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("phonecode")
    private String phonecode;

    public MediaAgendaRequest(String phone_number, String phonecode) {
        this.phone_number = phone_number;
        this.phonecode = phonecode;
    }
}
