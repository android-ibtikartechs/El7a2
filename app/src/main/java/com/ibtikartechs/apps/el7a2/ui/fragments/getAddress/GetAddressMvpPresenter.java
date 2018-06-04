package com.ibtikartechs.apps.el7a2.ui.fragments.getAddress;

import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpPresenter;


public interface GetAddressMvpPresenter <V extends GetAddressMvpView> extends MvpPresenter<V> {
    void getListAddresses ();
}
