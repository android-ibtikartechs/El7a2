package com.ibtikartechs.apps.el7a2.ui.fragments.maindeal;

import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

/**
 * Created by ahmedyehya on 5/3/18.
 */

public class MainDealPresenter <V extends MainDealMvpView> extends BasePresenter<V> implements MainDealMvpPresenter<V> {

    private final String TAG = MainDealPresenter.class.getSimpleName();

    public MainDealPresenter(DataManager dataManager) {
        super(dataManager);
    }

}
