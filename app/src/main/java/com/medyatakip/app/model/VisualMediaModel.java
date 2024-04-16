package com.medyatakip.app.model;

import java.util.List;

public class VisualMediaModel {


    private final String name;
    private final int count;
    private final List<SubVisualMediaModel> itemData4;


    public VisualMediaModel(String name, int count, List<SubVisualMediaModel> itemData4) {
        this.name = name;
        this.count = count;
        this.itemData4 = itemData4;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public List<SubVisualMediaModel> getItemData4() {
        return itemData4;
    }
}
