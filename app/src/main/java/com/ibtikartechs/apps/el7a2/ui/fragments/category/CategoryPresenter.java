package com.ibtikartechs.apps.el7a2.ui.fragments.category;

import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

import java.util.logging.Handler;

/**
 * Created by ahmedyehya on 5/9/18.
 */

public class CategoryPresenter <V extends CategoryMvpView> extends BasePresenter<V> implements CategoryMvpPresenter<V> {
    public CategoryPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getSubCategories(final String id) {
        getMvpView().showProgressBar();

        if (id.equals("34")) {
            getMvpView().addSubCategoryFragment("0");
            getMvpView().addSubCategoryFragment("1");
            getMvpView().addSubCategoryFragment("2");

            getMvpView().addSubCategoryTab("رحلات", 0);
            getMvpView().addSubCategoryTab("مواصلات", 1);
            getMvpView().addSubCategoryTab("فنادق", 2);
            getMvpView().setupSubCategoryTabs();
                }
        getMvpView().hideErrorView();

    }
}
