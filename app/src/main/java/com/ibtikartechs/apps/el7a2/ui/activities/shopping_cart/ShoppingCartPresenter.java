package com.ibtikartechs.apps.el7a2.ui.activities.shopping_cart;

import android.net.Uri;

import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.models.CartListModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

import java.util.ArrayList;

/**
 * Created by ahmedyehya on 5/13/18.
 */

public class ShoppingCartPresenter <V extends ShoppingCartMvpView> extends BasePresenter<V> implements ShoppingCartMvpPresenter<V> {
    public ShoppingCartPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public ArrayList<CartListModel> getCartList() {
        return (getDataManager().getCartItemsList());
    }

    @Override
    public int deleteItemFromCartList(Uri uri) {
        getDataManager().deleteItemFromOrderList(uri);
        return getNumberOfItemsCartList();
    }

    @Override
    public int getNumberOfItemsCartList() {
        return getDataManager().getNumberOfItemList();
    }

    @Override
    public void updateAmountOfItem(Uri uri, String amount) {
        getDataManager().updateAmountOfItem(uri,amount);
    }


    //TODO it is not here but for test
    @Override
    public void addItemToCart(String id, String title, String price, String imgUrl) {
        getDataManager().addItemToCart(id, title, price, imgUrl);
    }
}
