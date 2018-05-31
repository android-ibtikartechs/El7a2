package com.ibtikartechs.apps.el7a2.data.models;

public class SearchProductModel {
    private String productName;
    private String productId;

    public SearchProductModel(String productName, String productId) {
        this.productName = productName;
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductId() {
        return productId;
    }
}
