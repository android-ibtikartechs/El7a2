package com.ibtikartechs.apps.el7a2.ui.fragments.category;

import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpPresenter;

/**
 * Created by ahmedyehya on 5/9/18.
 */

public interface CategoryMvpPresenter <V extends CategoryMvpView> extends MvpPresenter<V> {
    void getSubCategories(String id);
}
