package com.example.mtm.model;

import java.util.List;

public class InternetModel {


    private final String name;
    private final int count;
    private final List<InternetSubListModel> itemData4;


    public InternetModel(String name, int count, List<InternetSubListModel> itemData4) {
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

    public List<InternetSubListModel> getItemData4() {
        return itemData4;
    }
}
