package com.ibtikartechs.apps.el7a2.ui.activities.main;

import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpPresenter;

/**
 * Created by ahmedyehya on 5/1/18.
 */

public interface MainMvpPresenter <V extends MainMvpView> extends MvpPresenter<V> {
    void getCategories();
    Integer getNumberOfItemsInCart();
    String getUserEmail();
}
