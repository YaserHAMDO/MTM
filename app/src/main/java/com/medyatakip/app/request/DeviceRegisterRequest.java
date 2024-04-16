package com.medyatakip.app.request;

import com.google.gson.annotations.SerializedName;

public class DeviceRegisterRequest {

    private int customerId;
    private String deviceToken;
    private String osType;

    public DeviceRegisterRequest(int customerId, String deviceToken, String osType) {
        this.customerId = customerId;
        this.deviceToken = deviceToken;
        this.osType = osType;
    }
}
