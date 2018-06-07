package com.ibtikartechs.apps.el7a2.ui.activities.main_deal_deatails;

import android.net.Uri;

import com.ibtikartechs.apps.el7a2.data.models.CartListModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpPresenter;
import com.ibtikartechs.apps.el7a2.ui.fragments.maindeal.MainDealMvpView;

/**
 * Created by ahmedyehya on 5/16/18.
 */

public interface MainDealDetailsMvpPresenter<V extends MainDealDetailsMvpView> extends MvpPresenter<V> {
    void getDeal(String id);
    void getProduct(String id);
    void addItemToCart(String id, String title, String price, String imgUrl, String quantity);
    void getFooter(String Id, String path);
    Integer getNumberOfItemsInCart();
    CartListModel checkIfExistInShoppingCart(String itemId);
    void updateAmountOfItem(String dbId, String amount);
}
