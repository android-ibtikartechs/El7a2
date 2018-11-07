package com.ibtikartechs.apps.am.ui.fragments.category;

import com.ibtikartechs.apps.am.ui.activities.base.MvpPresenter;

/**
 * Created by ahmedyehya on 5/9/18.
 */

public interface CategoryMvpPresenter <V extends CategoryMvpView> extends MvpPresenter<V> {
    void getSubCategories(String id);
}
