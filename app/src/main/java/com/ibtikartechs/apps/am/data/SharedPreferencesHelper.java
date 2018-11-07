package com.ibtikartechs.apps.am.data;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ahmedyehya on 4/30/18.
 */

public class SharedPreferencesHelper {
    public static final String MY_PREFS = "MY_PREFS";
    SharedPreferences mSharedPreferences;
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";


    public SharedPreferencesHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE);
    }


    public void clear()
    {
        mSharedPreferences.edit().clear().apply();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        mSharedPreferences.edit().putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime).apply();
    }

    public boolean isFirstTimeLaunch (){
        return mSharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH,true);
    }

}
