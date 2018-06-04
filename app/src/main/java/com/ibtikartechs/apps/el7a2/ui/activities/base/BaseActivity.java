package com.ibtikartechs.apps.el7a2.ui.activities.base;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ibtikartechs.apps.el7a2.ui_utilities.LoadingDialogFragment;

import java.util.Locale;

/**
 * Created by ahmedyehya on 4/30/18.
 */

public class BaseActivity extends AppCompatActivity implements MvpView {
    private Handler mHandler;
    LoadingDialogFragment loadingDialogFragment;

    @Override
    protected void attachBaseContext(Context newBase) {
        String lang_code = "ar"; //load it from SharedPref
        Context context = changeLang(newBase, lang_code);
        super.attachBaseContext(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }


    @Override
    public void showLoadingDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.setCancelable(false);
        loadingDialogFragment.show(fragmentManager,"loading");


    }

    @Override
    public void hideLoadingDialog() {
        mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                loadingDialogFragment.dismiss();
            }
        });
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
            String country = "ARE";
            Locale locale = new Locale(lang_code,country);
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
