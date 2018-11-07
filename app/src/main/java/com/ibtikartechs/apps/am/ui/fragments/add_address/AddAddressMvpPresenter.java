package com.ibtikartechs.apps.am.ui.fragments.add_address;

import com.ibtikartechs.apps.am.data.models.UserModel;
import com.ibtikartechs.apps.am.ui.activities.base.MvpPresenter;

public interface AddAddressMvpPresenter <V extends AddAddressMvpView> extends MvpPresenter<V> {
    void addAddress(String name, String cityId, String postalCode, String latitude, String longitude, String mobNum, String address);
    UserModel getUserData();
}
