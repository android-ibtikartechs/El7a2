package com.ibtikartechs.apps.el7a2.data.models;

/**
 * Created by ahmedyehya on 5/4/18.
 */

public class FooterListItemModel {
    private String price;
    private String imgUrl;
    private String description;
    private String endDate;
    private String id;

    public FooterListItemModel(String id, String price, String imgUrl, String description, String endDate) {
        this.price = price;
        this.imgUrl = imgUrl;
        this.description = description;
        this.endDate = endDate;
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getId() {
        return id;
    }
}
