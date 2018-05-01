package com.ibtikartechs.apps.el7a2.ui.activities.base;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ibtikartechs.apps.el7a2.ui_utilities.LoadingDialogFragment;

/**
 * Created by ahmedyehya on 4/30/18.
 */

public class BaseActivity extends AppCompatActivity implements MvpView {
    private Handler mHandler;
    LoadingDialogFragment loadingDialogFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        mHandler = new Handler(Looper.getMainLooper());
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
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                loadingDialogFragment.dismiss();
            }
        });
    }
}
