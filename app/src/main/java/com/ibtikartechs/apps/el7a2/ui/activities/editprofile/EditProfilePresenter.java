package com.ibtikartechs.apps.el7a2.ui.activities.editprofile;

import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

public class EditProfilePresenter <V extends EditProfileMvpView> extends BasePresenter<V> implements EditProfileMvpPresenter<V> {
    public EditProfilePresenter(DataManager dataManager) {
        super(dataManager);
    }

}
