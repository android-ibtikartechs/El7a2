package com.ibtikartechs.apps.el7a2.data.models;

public class GovernroteModel {
    private String cityName;
    private String id;

    public GovernroteModel(String cityName, String id) {
        this.cityName = cityName;
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public String getId() {
        return id;
    }
}
