package com.example.mtm.model;

public class MediaAgendaModel {

    private final String type;
    private final String imageUrl;
    private final String magazineImageUrl;
    private final String videoUrl;
    private final String title;
    private final String body;
    private final String gnoHash;
    private final String date;

    public MediaAgendaModel(String type, String imageUrl, String magazineImageUrl, String videoUrl, String title, String body, String gnoHash, String date) {
        this.type = type;
        this.imageUrl = imageUrl;
        this.magazineImageUrl = magazineImageUrl;
        this.videoUrl = videoUrl;
        this.title = title;
        this.body = body;
        this.gnoHash = gnoHash;
        this.date = date;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getMagazineImageUrl() {
        return magazineImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getGnoHash() {
        return gnoHash;
    }

    public String getDate() {
        return date;
    }
}
