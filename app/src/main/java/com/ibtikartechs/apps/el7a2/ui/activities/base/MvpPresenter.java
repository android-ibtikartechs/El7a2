package com.ibtikartechs.apps.el7a2.ui.activities.base;

/**
 * Created by ahmedyehya on 4/30/18.
 */

public interface MvpPresenter <V extends MvpView> {
    void onAttach(V mvpView);
}
