package com.ibtikartechs.apps.el7a2.ui.fragments.subcategory;

import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpPresenter;

/**
 * Created by ahmedyehya on 5/9/18.
 */

public interface SubCategoryMvpPresenter <V extends SubCategoryMvpView> extends MvpPresenter<V> {
    void loadFirstPage(String subCategoryId);
    void loadNextPage(String id, Integer page);
}
