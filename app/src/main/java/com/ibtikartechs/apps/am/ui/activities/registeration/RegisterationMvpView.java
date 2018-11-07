package com.ibtikartechs.apps.am.ui.activities.registeration;

import com.ibtikartechs.apps.am.ui.activities.base.MvpView;

public interface RegisterationMvpView extends MvpView {
    void showToast (String mesg);
    void showLoadingDialog(String msg);
    void showProgressDialog(String msg);
    void hideProgressDialog();
    void showActivationLinkDialog(String msg, String buttonTitle, String email, int buttonActionFlag);
    void completeAfterLogin();
}
