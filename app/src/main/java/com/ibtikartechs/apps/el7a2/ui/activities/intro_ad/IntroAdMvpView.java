package com.ibtikartechs.apps.el7a2.ui.activities.intro_ad;

import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpView;

public interface IntroAdMvpView extends MvpView {
    void startCountDown();
    void setBannerView(String imgUrl);
    void hideProgressBar();
}
