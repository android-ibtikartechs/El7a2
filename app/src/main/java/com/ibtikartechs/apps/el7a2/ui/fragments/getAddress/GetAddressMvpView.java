package com.ibtikartechs.apps.el7a2.ui.fragments.getAddress;

import com.ibtikartechs.apps.el7a2.data.models.AddressModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpView;

import java.util.ArrayList;

public interface GetAddressMvpView  extends MvpView {
    void populateAddressesListView(ArrayList<AddressModel> addressesArrayList);
}
