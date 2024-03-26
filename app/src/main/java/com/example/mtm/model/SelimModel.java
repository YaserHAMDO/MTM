package com.example.mtm.model;

public class SelimModel {
    String firstPageUrl;
    String name;
    String date;

    public SelimModel(String firstPageUrl, String name, String date) {
        this.firstPageUrl = firstPageUrl;
        this.name = name;
        this.date = date;
    }

    public String getFirstPageUrl() {
        return firstPageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
