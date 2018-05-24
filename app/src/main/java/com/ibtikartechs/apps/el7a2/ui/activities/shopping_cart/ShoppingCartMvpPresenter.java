package com.ibtikartechs.apps.el7a2.ui.activities.shopping_cart;

import android.net.Uri;

import com.ibtikartechs.apps.el7a2.data.models.CartListModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpPresenter;

import java.util.ArrayList;

/**
 * Created by ahmedyehya on 5/13/18.
 */

public interface ShoppingCartMvpPresenter <V extends ShoppingCartMvpView> extends MvpPresenter<V> {
    ArrayList<CartListModel> getCartList();
    int deleteItemFromCartList(Uri uri);
    int getNumberOfItemsCartList();
    void updateAmountOfItem(Uri uri, String amount);


}
