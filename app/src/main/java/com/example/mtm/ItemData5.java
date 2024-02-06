package com.example.mtm;

import java.util.List;

public class ItemData5 {


    private final String name;
    private final int count;
    private final List<ItemData4> itemData4;


    public ItemData5(String name, int count, List<ItemData4> itemData4) {
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

    public List<ItemData4> getItemData4() {
        return itemData4;
    }
}
