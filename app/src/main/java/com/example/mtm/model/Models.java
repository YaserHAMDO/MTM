package com.example.mtm.model;

public class Models {

    private final String type;
    private final String imageUrl;
    private final String magazineImageUrl;
    private final String videoUrl;
    private final String title;
    private final String body;

    public Models(String type, String imageUrl, String magazineImageUrl, String videoUrl, String title, String body) {
        this.type = type;
        this.imageUrl = imageUrl;
        this.magazineImageUrl = magazineImageUrl;
        this.videoUrl = videoUrl;
        this.title = title;
        this.body = body;
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
}
