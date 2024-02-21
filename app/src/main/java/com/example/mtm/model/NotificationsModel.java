package com.example.mtm.model;

public class NotificationsModel {

    private final String title;
    private final String body;
    private final String url;
    private final int id;
    private boolean read;

    public NotificationsModel(String title, String body, String url, int id, boolean read) {
        this.title = title;
        this.body = body;
        this.url = url;
        this.id = id;
        this.read = read;
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
