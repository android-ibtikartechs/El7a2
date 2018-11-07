package com.ibtikartechs.apps.am.ui.fragments.getAddress;

import com.ibtikartechs.apps.am.data.models.AddressModel;
import com.ibtikartechs.apps.am.ui.activities.base.MvpView;

import java.util.ArrayList;

public interface GetAddressMvpView  extends MvpView {
    void populateAddressesListView(ArrayList<AddressModel> addressesArrayList);
}
