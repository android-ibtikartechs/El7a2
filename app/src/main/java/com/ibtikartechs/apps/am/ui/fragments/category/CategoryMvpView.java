package com.ibtikartechs.apps.am.ui.fragments.category;

import com.ibtikartechs.apps.am.ui.activities.base.MvpView;

/**
 * Created by ahmedyehya on 5/9/18.
 */

public interface CategoryMvpView extends MvpView {
    void hideErrorView();
    void showErrorView();
    String fetchErrorMessage();
    void showProgressBar();
    void addSubCategoryTab(String title,int tabIndex);
    void addSubCategoryFragment(String id);
    void setupSubCategoryTabs();
}
