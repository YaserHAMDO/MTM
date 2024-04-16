package com.medyatakip.app.model;

import java.util.ArrayList;

public class MediaAgendaModel {

    private final String type;
    private final String imageUrl;

    private final String magazineImageUrl;
    private final String videoUrl;
    private final String title;
    private final String body;
    private final String gnoHash;
    private final String date;

    private final ArrayList<String> clipImages;
    private final ArrayList<String> fullImages;

    public MediaAgendaModel(String type, String imageUrl, String magazineImageUrl, String videoUrl, String title, String body, String gnoHash, String date, ArrayList<String> clipImages, ArrayList<String> fullImages) {
        this.type = type;
        this.imageUrl = imageUrl;
        this.magazineImageUrl = magazineImageUrl;
        this.videoUrl = videoUrl;
        this.title = title;
        this.body = body;
        this.gnoHash = gnoHash;
        this.date = date;
        this.clipImages = clipImages;
        this.fullImages = fullImages;
    }


    public ArrayList<String> getClipImages() {
        return clipImages;
    }

    public ArrayList<String> getFullImages() {
        return fullImages;
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
