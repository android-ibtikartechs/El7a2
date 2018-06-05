package com.ibtikartechs.apps.el7a2.ui.activities.intro;

import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpPresenter;

public interface IntroMvpPresenter <V extends IntroMvpView> extends MvpPresenter<V> {
    boolean isFirstTimeLaunch ();
    void  setFirstTimeLaunch(boolean isFirstTimeLaunch);

}
