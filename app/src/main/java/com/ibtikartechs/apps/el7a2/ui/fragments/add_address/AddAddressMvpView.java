package com.ibtikartechs.apps.el7a2.ui.fragments.add_address;

import com.ibtikartechs.apps.el7a2.data.models.AddressModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpView;

public interface AddAddressMvpView extends MvpView {
    void showToast(String msg);
    void refreshLocationPermision();
    void showProgressDialog(final String msg);
    void hideProgressDialog();
    void passAddress (AddressModel addressModel);
}
