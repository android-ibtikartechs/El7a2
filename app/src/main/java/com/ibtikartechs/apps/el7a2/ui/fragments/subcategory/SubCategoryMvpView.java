package com.ibtikartechs.apps.el7a2.ui.fragments.subcategory;

import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpView;

/**
 * Created by ahmedyehya on 5/9/18.
 */

public interface SubCategoryMvpView extends MvpView {
    void hideErrorView();
    void showErrorView();
    String fetchErrorMessage();
    void showProgressBar();
}
