package com.medyatakip.app.response;



public class DeviceRegisterResponse {

    public boolean data;
    public Response response;

    public boolean isData() {
        return data;
    }

    public Response getResponse() {
        return response;
    }

    public class Response{
        public boolean status;

        public boolean isStatus() {
            return status;
        }
    }

}
