package com.ibtikartechs.apps.am.ui.activities.more_products;

import com.ibtikartechs.apps.am.ui.activities.base.MvpPresenter;

public interface MoreProductsMvpPresenter<V extends MoreProductsMvpView> extends MvpPresenter<V> {
    Integer getNumberOfItemsInCart ();
    void getProductsListFirstPage (String id);
    void getNextPage(String id, Integer page);
}
