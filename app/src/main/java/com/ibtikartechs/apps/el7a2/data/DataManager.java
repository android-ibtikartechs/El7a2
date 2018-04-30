package com.ibtikartechs.apps.el7a2.data;

import com.ibtikartechs.apps.el7a2.data.db_helper.SQLiteHandler;

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
}
