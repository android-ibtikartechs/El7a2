package com.ibtikartechs.apps.el7a2.ui.activities.registeration;

import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpPresenter;

public interface RegisterationMvpPresenter <V extends RegisterationMvpView> extends MvpPresenter<V> {
    void signupRequest(String name, String email, String mobNum, String address, String cityId, String password, String confPassword);
    void loginRequest(String email, String password);


}
