package com.example.mtm.model;

public class SubInternetModel {

    private final String journalistName;
    private final String mediaName;
    private final String title;
    private final String date;
    private final String imageStoragePath;
    private final String shareLink;
    private final String shareLink2;
    private final String programName;

    public SubInternetModel(String journalistName, String mediaName, String title, String date, String imageStoragePath, String shareLink, String shareLink2, String programName) {
        this.journalistName = journalistName;
        this.mediaName = mediaName;
        this.title = title;
        this.date = date;
        this.imageStoragePath = imageStoragePath;
        this.shareLink = shareLink;
        this.shareLink2 = shareLink2;
        this.programName = programName;
    }

    public String getProgramName() {
        return programName;
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

    public String getHariciShareLink() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getShareLink() {
        return shareLink;
    }

    public String getShareLink2() {
        return shareLink2;
    }
}
