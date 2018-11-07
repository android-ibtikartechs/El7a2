package com.ibtikartechs.apps.am.ui.activities.main;

import com.ibtikartechs.apps.am.ui.activities.base.MvpPresenter;

import io.reactivex.disposables.Disposable;

/**
 * Created by ahmedyehya on 5/1/18.
 */

public interface MainMvpPresenter <V extends MainMvpView> extends MvpPresenter<V> {
    void getCategories();
    Integer getNumberOfItemsInCart();
    String getUserEmail();
    Integer getCartItemsNumObserv();
    void logout();
}
