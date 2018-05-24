package com.ibtikartechs.apps.el7a2.data;

import android.net.Uri;

import com.ibtikartechs.apps.el7a2.data.db_helper.SQLiteHandler;
import com.ibtikartechs.apps.el7a2.data.models.CartListModel;

import java.util.ArrayList;

/**
 * Created by ahmedyehya on 4/30/18.
 */

public class DataManager {
    private SharedPreferencesHelper sharedPrefsHelper;
    private SQLiteHandler mSQLiteHandler;

    public DataManager(SQLiteHandler mSQLiteHandler, SharedPreferencesHelper sharedPrefsHelper) {
        this.sharedPrefsHelper = sharedPrefsHelper;
        this.mSQLiteHandler = mSQLiteHandler;
    }

    public void addItemToCart(String id, String title, String price, String imgUrl, String quantity)
    {
        mSQLiteHandler.addItemsToCart(id,title,price,imgUrl, quantity);
    }

    public ArrayList<CartListModel> getCartItemsList()
    {
        return mSQLiteHandler.getCartList();
    }

    public boolean isItemExistinInCart (String itemId)
    {
        ArrayList<CartListModel> cartItemsList = getCartItemsList();
        boolean isExist = false;
        for (int i = 0 ; i<cartItemsList.size() ; i++)
        {
            if ((itemId).equals(cartItemsList.get(i).getId())) {
                isExist = true;
                break;
            }

        }
        return isExist;
    }

    public Integer getNumberOfItemList ()
    {
        return mSQLiteHandler.getCartList().size();
    }

    public void deleteItemFromOrderList(Uri uri)
    {
        mSQLiteHandler.deleteItemsfromCart(uri);
    }

    public void updateAmountOfItem (Uri uri, String amount)
    {
        mSQLiteHandler.editAmountofItem(uri,amount);
    }

}
