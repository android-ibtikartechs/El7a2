package com.ibtikartechs.apps.am.ui.activities.editprofile;

import com.ibtikartechs.apps.am.data.DataManager;
import com.ibtikartechs.apps.am.ui.activities.base.BasePresenter;

public class EditProfilePresenter <V extends EditProfileMvpView> extends BasePresenter<V> implements EditProfileMvpPresenter<V> {
    public EditProfilePresenter(DataManager dataManager) {
        super(dataManager);
    }

}
