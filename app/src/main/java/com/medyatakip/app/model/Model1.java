package com.medyatakip.app.model;

public class Model1 {
    String firstPageUrl;
    String name;
    String date;

    public Model1(String firstPageUrl, String name, String date) {
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
