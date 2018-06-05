package com.ibtikartechs.apps.el7a2.ui.activities.main_deal_deatails;

import com.ibtikartechs.apps.el7a2.data.models.FooterListItemModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpView;

import java.util.ArrayList;

/**
 * Created by ahmedyehya on 5/16/18.
 */

public interface MainDealDetailsMvpView extends MvpView {
    void hideErrorView();
    void showErrorView();
    String fetchErrorMessage();
    void showProgressBar();
    void populateData(String dealName, String mainImageUrl, String price, String oldPrice, String discountPercent, String mainDescription, String imgUrlBanner, boolean likeStatus);
    void addMoreToAdapter(ArrayList<FooterListItemModel> list);
    void hideFooterProgressBar();
    void showFooterProgressBar();
    void showFooterErrorView();
    void hideFooterErrorView();
    void setTimer(String time);
    void setSupplement(String imgUrl1, String imgUrl2);
    void setFeaturesContent(String Features, String content);
    void setFooterId(String footerCatId , String footerCatName);
    void showFooter();
}
