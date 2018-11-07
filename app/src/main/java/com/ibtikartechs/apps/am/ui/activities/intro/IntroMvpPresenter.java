package com.ibtikartechs.apps.am.ui.activities.intro;

import com.ibtikartechs.apps.am.ui.activities.base.MvpPresenter;

public interface IntroMvpPresenter <V extends IntroMvpView> extends MvpPresenter<V> {
    boolean isFirstTimeLaunch ();
    void  setFirstTimeLaunch(boolean isFirstTimeLaunch);

}
