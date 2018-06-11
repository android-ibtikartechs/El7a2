package com.ibtikartechs.apps.el7a2.ui.activities.intro_ad;

import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpPresenter;

public interface IntroAdMvpPresenter <V extends IntroAdMvpView> extends MvpPresenter<V> {
    void getBannerAd(float density);
}
