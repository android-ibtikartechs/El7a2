package com.ibtikartechs.apps.el7a2;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.SharedPreferencesHelper;
import com.ibtikartechs.apps.el7a2.data.db_helper.SQLiteHandler;

import java.util.Locale;

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
        String lang_code = "ar"; //load it from SharedPref
        Context context = changeLang(getApplicationContext(), lang_code);
    }

    public DataManager getDataManager() {
        return dataManager;
    }


    public ContextWrapper changeLang(Context context, String lang_code) {
        Locale sysLocale;

        Resources rs = context.getResources();
        Configuration config = rs.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = config.getLocales().get(0);
        } else {
            sysLocale = config.locale;
        }
        if (!lang_code.equals("") && !sysLocale.getLanguage().equals(lang_code)) {
            String country = "EG";
            Locale locale = new Locale(lang_code);
            Locale.setDefault(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(locale);
            } else {
                config.locale = locale;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLayoutDirection(locale);
                context = context.createConfigurationContext(config);
            } else {
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            }

        }

        return new ContextWrapper(context);
    }

}