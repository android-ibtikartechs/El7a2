package com.ibtikartechs.apps.el7a2.ui.activities.base;


import android.annotation.TargetApi;
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
import com.ibtikartechs.apps.el7a2.utilities.LanguageHelper;

import java.util.Locale;

/**
 * Created by ahmedyehya on 4/30/18.
 */

public class BaseActivity extends AppCompatActivity implements MvpView {
    private Handler mHandler;
    LoadingDialogFragment loadingDialogFragment;

    @Override
    protected void attachBaseContext(Context newBase) {
       /* String lang_code = "ar"; //load it from SharedPref
        Locale locale = new Locale(lang_code);
        MyContextWrapper myContextWrapper = new MyContextWrapper(newBase);
        Context context = myContextWrapper.wrap(newBase,lang_code);
        super.attachBaseContext(context); */
        Context context = LanguageHelper.updateLanguage(newBase, "ar_SA");
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

    private static Context setLocale(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
            context.createConfigurationContext(configuration);

        } else {
            configuration.locale = locale;
            context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        }
        return context;
    }



    public class MyContextWrapper extends ContextWrapper {

        public MyContextWrapper(Context base) {
            super(base);
        }

        @SuppressWarnings("deprecation")
        public  ContextWrapper wrap(Context context, String language) {
            Configuration config = context.getResources().getConfiguration();
            Locale sysLocale = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                sysLocale = getSystemLocale(config);
            } else {
                sysLocale = getSystemLocaleLegacy(config);
            }
            if (!language.equals("") && !sysLocale.getLanguage().equals(language)) {
                Locale locale = new Locale(language);
                Locale.setDefault(locale);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setSystemLocale(config, locale);
                } else {
                    setSystemLocaleLegacy(config, locale);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    context = context.createConfigurationContext(config);
                } else {
                    context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
                }
            }
            return new MyContextWrapper(context);
        }

        @SuppressWarnings("deprecation")
        public  Locale getSystemLocaleLegacy(Configuration config){
            return config.locale;
        }

        @TargetApi(Build.VERSION_CODES.N)
        public  Locale getSystemLocale(Configuration config){
            return config.getLocales().get(0);
        }

        @SuppressWarnings("deprecation")
        public  void setSystemLocaleLegacy(Configuration config, Locale locale){
            config.locale = locale;
        }

        @TargetApi(Build.VERSION_CODES.N)
        public  void setSystemLocale(Configuration config, Locale locale){
            config.setLocale(locale);
        }
    }




}
