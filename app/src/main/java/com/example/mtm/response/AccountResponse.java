package com.example.mtm.response;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AccountResponse {

    public ArrayList<Datum> data;
    public Response response;

    public ArrayList<Datum> getData() {
        return data;
    }

    public Response getResponse() {
        return response;
    }

    public class Datum{
        public int id;
        public String name;
        public boolean active;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public boolean isActive() {
            return active;
        }
    }

    public class Response{
        public boolean status;

        public boolean isStatus() {
            return status;
        }
    }

}
