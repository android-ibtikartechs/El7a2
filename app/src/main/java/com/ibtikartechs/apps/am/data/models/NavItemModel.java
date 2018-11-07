package com.ibtikartechs.apps.am.data.models;

/**
 * Created by ahmedyehya on 5/1/18.
 */

public class NavItemModel  {
    private String name;
    private int iconResourceId;

    public NavItemModel(String name, int iconResourceId) {
        this.name = name;
        this.iconResourceId = iconResourceId;
    }

    public String getName() {
        return name;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }
}
