package com.ibtikartechs.apps.el7a2.ui.activities.base;

import com.ibtikartechs.apps.el7a2.data.DataManager;

/**
 * Created by ahmedyehya on 4/30/18.
 */

public class BasePresenter <V extends MvpView> implements MvpPresenter<V> {
    DataManager mDataManager;
    private V mMvpView;
    @Override
    public void onAttach(V mvpView) {
    }

    public V getMvpView() {
        return mMvpView;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }
}