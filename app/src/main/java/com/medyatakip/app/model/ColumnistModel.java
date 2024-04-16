package com.medyatakip.app.model;

public class ColumnistModel {


    private final String journalistImageUrl;
    private final String journalistName;
    private final String mediaName;
    private final String title;
    private final String date;
    private final String imageStoragePath;
    private final String gnoHash;

    public ColumnistModel(String journalistImageUrl, String journalistName, String mediaName, String title, String date, String imageStoragePath, String gnoHash) {
        this.journalistImageUrl = journalistImageUrl;
        this.journalistName = journalistName;
        this.mediaName = mediaName;
        this.title = title;
        this.date = date;
        this.imageStoragePath = imageStoragePath;
        this.gnoHash = gnoHash;
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

    public String getGnoHash() {
        return gnoHash;
    }
}
