package com.ibtikartechs.apps.am.ui.activities.base;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ibtikartechs.apps.am.ui_utilities.LoadingDialogFragment;

/**
 * Created by ahmedyehya on 5/3/18.
 */

public class BaseFragment extends Fragment implements MvpView {
    private Handler mHandler;
    LoadingDialogFragment loadingDialogFragment;
    @Override
    public void showLoadingDialog() {
        FragmentManager fragmentManager = getChildFragmentManager();
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
}
