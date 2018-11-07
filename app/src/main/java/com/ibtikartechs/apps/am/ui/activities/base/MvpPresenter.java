package com.ibtikartechs.apps.am.ui.activities.base;

/**
 * Created by ahmedyehya on 4/30/18.
 */

public interface MvpPresenter <V extends MvpView> {
    void onAttach(V mvpView);
}
