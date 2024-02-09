package com.example.mtm.model;

public class SubInternetModel {

    private final String journalistName;
    private final String mediaName;
    private final String title;
    private final String date;
    private final String imageStoragePath;
    private final String shareLink;

    public SubInternetModel(String journalistName, String mediaName, String title, String date, String imageStoragePath, String shareLink) {
        this.journalistName = journalistName;
        this.mediaName = mediaName;
        this.title = title;
        this.date = date;
        this.imageStoragePath = imageStoragePath;
        this.shareLink = shareLink;
    }

    public String getImageStoragePath() {
        return imageStoragePath;
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

    public String getShareLink() {
        return shareLink;
    }
}
