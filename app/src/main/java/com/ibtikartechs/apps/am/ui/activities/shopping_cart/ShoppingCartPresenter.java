package com.ibtikartechs.apps.am.ui.activities.shopping_cart;

import android.net.Uri;

import com.ibtikartechs.apps.am.data.DataManager;
import com.ibtikartechs.apps.am.data.models.CartListModel;
import com.ibtikartechs.apps.am.ui.activities.base.BasePresenter;

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
        getDataManager().deleteItemFromDataBase(uri);
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

    @Override
    public boolean isUserLogedIn() {
        return getDataManager().isUserLogedIn();
    }


}
