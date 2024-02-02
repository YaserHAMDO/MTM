package com.example.mtm;

public class ItemData2 {


    private final String imageUrl;
    private final String name;
    private final String mediaPath;
    private final String pageFile;
    private final String gno;

    public ItemData2(String imageUrl, String name, String mediaPath, String pageFile, String gno) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.mediaPath = mediaPath;
        this.pageFile = pageFile;
        this.gno = gno;
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
}
