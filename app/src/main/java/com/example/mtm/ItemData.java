package com.example.mtm;

public class ItemData {
    private int imageResId;
    private int backgroundColor;
    private String text;

    public ItemData(int imageResId, int backgroundColor, String text) {
        this.imageResId = imageResId;
        this.backgroundColor = backgroundColor;
        this.text = text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public String getText() {
        return text;
    }
}
