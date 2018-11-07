package com.ibtikartechs.apps.am.ui.activities.order_details;

import com.ibtikartechs.apps.am.ui.activities.base.MvpView;

public interface OrderDetailsMvpView extends MvpView {
    void hideErrorView();
    void showErrorView();
    String fetchErrorMessage();
    void showProgressBar();
    void populateData(String orderNumber, String orderDate, String deliveryPrice, String tax, String totalPrice);



}
