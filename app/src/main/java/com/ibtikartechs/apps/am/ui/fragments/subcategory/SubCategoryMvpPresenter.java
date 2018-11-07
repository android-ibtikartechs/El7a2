package com.ibtikartechs.apps.am.ui.fragments.subcategory;

import com.ibtikartechs.apps.am.ui.activities.base.MvpPresenter;

/**
 * Created by ahmedyehya on 5/9/18.
 */

public interface SubCategoryMvpPresenter <V extends SubCategoryMvpView> extends MvpPresenter<V> {
    void loadFirstPage(String subCategoryId);
    void loadNextPage(String id, Integer page);
}
