package com.ibtikartechs.apps.am.ui.fragments.add_address;

import com.ibtikartechs.apps.am.data.models.AddressModel;
import com.ibtikartechs.apps.am.ui.activities.base.MvpView;

public interface AddAddressMvpView extends MvpView {
    void showToast(String msg);
    void refreshLocationPermision();
    void showProgressDialog(final String msg);
    void hideProgressDialog();
    void passAddress (AddressModel addressModel);
}
