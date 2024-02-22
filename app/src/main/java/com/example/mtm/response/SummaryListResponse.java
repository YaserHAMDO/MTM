package com.example.mtm.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SummaryListResponse {

    public ArrayList<Datum> data;
    public Response response;

    public ArrayList<Datum> getData() {
        return data;
    }

    public Response getResponse() {
        return response;
    }

    public class Customer{
        public int id;
        public String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public class Datum{
        public int id;
        public Customer customer;
        public String name;
        public String uniqueid;
        public String mailUrl;

        public int getId() {
            return id;
        }

        public Customer getCustomer() {
            return customer;
        }

        public String getName() {
            return name;
        }

        public String getUniqueid() {
            return uniqueid;
        }

        public String getMailUrl() {
            return mailUrl;
        }
    }

    public class Response{
        public boolean status;

        public boolean isStatus() {
            return status;
        }
    }
}
