package com.ibtikartechs.apps.el7a2.data.models;

public class OrderItemModel {
    private String productName;
    private String productPrice;

    public OrderItemModel(String productName, String productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }
}
