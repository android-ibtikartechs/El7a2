package com.ibtikartechs.apps.el7a2.ui.activities.main;

import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpView;

/**
 * Created by ahmedyehya on 5/1/18.
 */

public interface MainMvpView extends MvpView {
    void addCategoryTab(String title,int tabIndex);
    void addCategoryFragment(String id);
    void addMainDealFragment();
    void showLoadingDialog();
    void hideLoadingDialog();
}
