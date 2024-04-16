package com.medyatakip.app.model;

public class NewspaperModel {

    private final String imageUrl;
    private final String name;
    private final String mediaPath;
    private final String pageFile;
    private final String gno;
    private final int count;

    public NewspaperModel(String imageUrl, String name, String mediaPath, String pageFile, String gno, int count) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.mediaPath = mediaPath;
        this.pageFile = pageFile;
        this.gno = gno;
        this.count = count;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public String getPageFile() {
        return pageFile;
    }

    public String getGno() {
        return gno;
    }

    public int getCount() {
        return count;
    }
}
