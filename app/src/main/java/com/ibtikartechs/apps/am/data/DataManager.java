package com.ibtikartechs.apps.am.data;

import android.net.Uri;
import android.util.Log;

import com.ibtikartechs.apps.am.data.db_helper.El7a2Contract;
import com.ibtikartechs.apps.am.data.db_helper.SQLiteHandler;
import com.ibtikartechs.apps.am.data.models.CartListModel;
import com.ibtikartechs.apps.am.data.models.UserModel;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

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

    public CartListModel getItemById(Uri uri) {
        return mSQLiteHandler.getItemById(uri);
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

    public Observable<Boolean> isItemExistInCartObserv(Uri uri) {
        return makeObservable(mSQLiteHandler.isItemExistInCartObserv(uri))
                .subscribeOn(Schedulers.computation()); // note: do not use Schedulers.io()
    }

    public Observable<Boolean> deleteFromDataBaseObserved(Uri uri)
    {
        return makeObservable(mSQLiteHandler.deleteFromDataBaseObserved(uri))
                .observeOn(Schedulers.computation());
    }

    public Observable<Integer> getCartItemsNumber()
    {
        return makeObservable(mSQLiteHandler.getCartItemsNumber())
                .observeOn(Schedulers.computation());
    }


    private static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new ObservableOnSubscribe<T>() {
                    @Override
                    public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                        try {
                            emitter.onNext(func.call());
                        } catch (Exception ex){
                            Log.e("db", "Error reading from the database", ex);
                        }
                    }
                });
    }

    public boolean isUserLogedIn()
    {
        return mSQLiteHandler.isUserLogedIn();
    }



    public void setFirstTimeLaunch(boolean isFirstTime)
    {
        sharedPrefsHelper.setFirstTimeLaunch(isFirstTime);
    }

}
