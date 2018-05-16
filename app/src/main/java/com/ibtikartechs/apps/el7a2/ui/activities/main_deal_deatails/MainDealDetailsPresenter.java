package com.ibtikartechs.apps.el7a2.ui.activities.main_deal_deatails;

import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

/**
 * Created by ahmedyehya on 5/16/18.
 */

public class MainDealDetailsPresenter <V extends MainDealDetailsMvpView> extends BasePresenter<V> implements MainDealDetailsMvpPresenter<V>{

    public MainDealDetailsPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
