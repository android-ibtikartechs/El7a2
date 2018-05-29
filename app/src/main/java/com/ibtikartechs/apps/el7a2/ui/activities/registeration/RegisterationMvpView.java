package com.ibtikartechs.apps.el7a2.ui.activities.registeration;

import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpView;

public interface RegisterationMvpView extends MvpView {
    void showToast (String mesg);
    void showLoadingDialog(String msg);
    void showProgressDialog(String msg);
    void hideProgressDialog();
    void showActivationLinkDialog(String msg, String buttonTitle, String email, int buttonActionFlag);
    void completeAfterLogin();
}
