package com.ibtikartechs.apps.am.ui.activities.intro_ad;

import com.ibtikartechs.apps.am.ui.activities.base.MvpView;

public interface IntroAdMvpView extends MvpView {
    void startCountDown();
    void setBannerView(String imgUrl);
    void hideProgressBar();
}
