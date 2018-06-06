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
    private boolean isDisplayTimer;
    String oldPrice;
    String discountPercent;
    private boolean liked;
    private String  dbId;

    public FooterListItemModel(String id, String price, String imgUrl, String description, String endDate) {
        this.price = price;
        this.imgUrl = imgUrl;
        this.description = description;
        this.endDate = endDate;
        this.id = id;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getDbId() {
        return dbId;
    }

    public FooterListItemModel(String price, String imgUrl, String description, String endDate, String id, boolean isDisplayTimer, String oldPrice, String discountPercent) {
        this.price = price;
        this.imgUrl = imgUrl;
        this.description = description;
        this.endDate = endDate;
        this.id = id;
        this.isDisplayTimer = isDisplayTimer;
        this.oldPrice = oldPrice;
        this.discountPercent = discountPercent;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isLiked() {
        return liked;
    }

    public boolean isDisplayTimer() {
        return isDisplayTimer;
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

    public String getOldPrice() {
        return oldPrice;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }
}
