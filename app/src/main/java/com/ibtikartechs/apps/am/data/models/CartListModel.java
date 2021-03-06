package com.ibtikartechs.apps.am.data.models;

/**
 * Created by ahmedyehya on 5/6/18.
 */

public class CartListModel  {
    private String id;
    private String title;
    private String price;
    private String img_url;
    private String dpId;
    private String amount;
    private int count;

    public CartListModel(String depId, String id, String title, String price, String img_url, String amount) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.img_url = img_url;
        this.dpId = depId;
        this.amount = amount;
    }

    public CartListModel() {

    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getDpId() {
        return dpId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
