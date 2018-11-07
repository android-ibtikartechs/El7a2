package com.ibtikartechs.apps.am.ui.activities.main;

import com.ibtikartechs.apps.am.ui.activities.base.MvpView;

/**
 * Created by ahmedyehya on 5/1/18.
 */

public interface MainMvpView extends MvpView {
    void addCategoryTab(String title,int tabIndex);
    void addCategoryFragment(String id);
    void addMainDealFragment();
    void showLoadingDialog();
    void hideLoadingDialog();
    void setupCategoryTabs();
    void hideErrorView();
    void showErrorView();
    String fetchErrorMessage();
    void showProgressBar();
    void closeDrawer();
}
