package com.medyatakip.app.model;

public class InternetSubListModel {


    private final String name;
    private final int count;
    private final String menuId;
    private final String subMenuId;


    public InternetSubListModel(String name, int count, String menuId, String subMenuId) {
        this.name = name;
        this.count = count;
        this.menuId = menuId;
        this.subMenuId = subMenuId;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public String getMenuId() {
        return menuId;
    }

    public String getSubMenuId() {
        return subMenuId;
    }
}
