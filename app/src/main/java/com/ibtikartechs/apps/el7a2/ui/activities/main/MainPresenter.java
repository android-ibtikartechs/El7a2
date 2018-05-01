package com.ibtikartechs.apps.el7a2.ui.activities.main;

import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

/**
 * Created by ahmedyehya on 5/1/18.
 */

public class MainPresenter <V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void getCategories() {
        //getMvpView().showLoadingDialog();
    }
}
