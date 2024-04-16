package com.medyatakip.app.model;

public class SliderModel {

    String imageUrl;
    String link;

    public SliderModel(String imageUrl, String link) {
        this.imageUrl = imageUrl;
        this.link = link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLink() {
        return link;
    }
}
