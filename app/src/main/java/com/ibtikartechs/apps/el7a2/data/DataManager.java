package com.ibtikartechs.apps.el7a2.data;

import android.net.Uri;

import com.ibtikartechs.apps.el7a2.data.db_helper.El7a2Contract;
import com.ibtikartechs.apps.el7a2.data.db_helper.SQLiteHandler;
import com.ibtikartechs.apps.el7a2.data.models.CartListModel;
import com.ibtikartechs.apps.el7a2.data.models.UserModel;

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

    public Uri addItemToCart(String id, String title, String price, String imgUrl, String quantity)
    {
        return mSQLiteHandler.addItemsToCart(id,title,price,imgUrl, quantity);
    }

    public void addUser(String id, String name, String email, String mobNum, String address, String gov, String city)
    {
        deleteItemFromDataBase(El7a2Contract.CartEntry.CONTENT_URI_USER_PATH);
        mSQLiteHandler.addUser(id,name,email,mobNum,address,gov,city);
    }

    public UserModel getUser(){
        return mSQLiteHandler.gryUser();
    }


    public ArrayList<CartListModel> getCartItemsList()
    {
        return mSQLiteHandler.getCartList();
    }

    public String isItemExistinInCart (String itemId)
    {
        String dbId = null;
        ArrayList<CartListModel> cartItemsList = getCartItemsList();
        //boolean isExist = false;
        for (int i = 0 ; i<cartItemsList.size() ; i++)
        {
            if ((itemId).equals(cartItemsList.get(i).getId())) {
                //isExist = true;
                dbId = cartItemsList.get(i).getDpId();
                break;
            }

        }
        return dbId;
    }

    public Integer getNumberOfItemList ()
    {
        return mSQLiteHandler.getCartList().size();
    }

    public void deleteItemFromDataBase(Uri uri)
    {
        mSQLiteHandler.deleteFromDataBase(uri);
    }


    public void updateAmountOfItem (Uri uri, String amount)
    {
        mSQLiteHandler.editAmountofItem(uri,amount);
    }

    public void ubdateUserData (Uri uri, String id, String name, String email, String mobNum, String address, String gov, String city)
    {
        mSQLiteHandler.editUserData(uri, id, name, email, mobNum, address, gov, city);
    }

    public boolean isItemExistInCart(Uri uri)
    {
        return mSQLiteHandler.isItemExistInCart(uri);
    }

    public boolean isFirstTimeLaunch()
    {
        return sharedPrefsHelper.isFirstTimeLaunch();
    }

    public void setFirstTimeLaunch(boolean isFirstTime)
    {
        sharedPrefsHelper.setFirstTimeLaunch(isFirstTime);
    }

}
