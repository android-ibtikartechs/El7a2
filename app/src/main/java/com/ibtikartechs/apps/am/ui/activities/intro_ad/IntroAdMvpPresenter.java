package com.ibtikartechs.apps.am.ui.activities.intro_ad;

import com.ibtikartechs.apps.am.ui.activities.base.MvpPresenter;

public interface IntroAdMvpPresenter <V extends IntroAdMvpView> extends MvpPresenter<V> {
    void getBannerAd(float density);
}
