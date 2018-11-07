package com.ibtikartechs.apps.am.ui.fragments.getAddress;

import com.ibtikartechs.apps.am.ui.activities.base.MvpPresenter;


public interface GetAddressMvpPresenter <V extends GetAddressMvpView> extends MvpPresenter<V> {
    void getListAddresses ();
}
