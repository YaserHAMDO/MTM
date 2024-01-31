package com.example.mtm;

public class ItemData {

    private int imageResId;
    private int backgroundColor;
    private String text;
    private boolean progressBarVisible;

    public ItemData(int imageResId, int backgroundColor, String text) {
        this.imageResId = imageResId;
        this.backgroundColor = backgroundColor;
        this.text = text;
        this.progressBarVisible = false; // Initially, progress bar is not visible
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

    public boolean isProgressBarVisible() {
        return progressBarVisible;
    }

    public void setProgressBarVisible(boolean visible) {
        this.progressBarVisible = visible;
    }
}
