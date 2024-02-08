package com.example.mtm.model;

public class ItemData3 {


    private final String journalistImageUrl;
    private final String journalistName;
    private final String mediaName;
    private final String title;
    private final String date;
    private final String imageStoragePath;

    public ItemData3(String journalistImageUrl, String journalistName, String mediaName, String title, String date, String imageStoragePath) {
        this.journalistImageUrl = journalistImageUrl;
        this.journalistName = journalistName;
        this.mediaName = mediaName;
        this.title = title;
        this.date = date;
        this.imageStoragePath = imageStoragePath;
    }

    public String getImageStoragePath() {
        return imageStoragePath;
    }

    public String getJournalistImageUrl() {
        return journalistImageUrl;
    }

    public String getJournalistName() {
        return journalistName;
    }

    public String getMediaName() {
        return mediaName;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
