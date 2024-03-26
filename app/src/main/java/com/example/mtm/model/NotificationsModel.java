package com.example.mtm.model;

public class NotificationsModel {

    private final String title;
    private final String body;
    private final String url;
    private final int id;
    private boolean read;
    private final String date;
    private final String time;

    public NotificationsModel(String title, String body, String url, int id, boolean read, String date, String time) {
        this.title = title;
        this.body = body;
        this.url = url;
        this.id = id;
        this.read = read;
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getUrl() {
        return url;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
