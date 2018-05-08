package com.ibtikartechs.apps.el7a2.ui.fragments.maindeal;

import com.ibtikartechs.apps.el7a2.data.models.FooterListItemModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpView;

import java.util.ArrayList;

/**
 * Created by ahmedyehya on 5/3/18.
 */

public interface MainDealMvpView extends MvpView {
    void hideErrorView();
    void showErrorView();
    void populateData(String productImgUrl, String productName, String productPrice,
                      String endDate, String firstSaleImgUrl,
                      String secondSaleImgUrl, String details);
    String fetchErrorMessage();
    void showProgressBar();
    void hideFooterProgressBar();
    void addMoreToAdapter(ArrayList<FooterListItemModel> list);

}
