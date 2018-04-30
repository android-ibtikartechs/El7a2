package com.ibtikartechs.apps.el7a2;

import android.app.Application;

import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.SharedPreferencesHelper;
import com.ibtikartechs.apps.el7a2.data.db_helper.SQLiteHandler;

/**
 * Created by ahmedyehya on 4/30/18.
 */

public class MvpApp extends Application {
    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesHelper sharedPrefsHelper = new SharedPreferencesHelper(getApplicationContext());
        SQLiteHandler sqliteHandler = new SQLiteHandler(getApplicationContext());
        dataManager = new DataManager(sqliteHandler, sharedPrefsHelper);
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}