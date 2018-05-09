package com.ibtikartechs.apps.el7a2.ui.fragments.subcategory;

import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

/**
 * Created by ahmedyehya on 5/9/18.
 */

public class SubCategoryPresenter <V extends SubCategoryMvpView> extends BasePresenter<V> implements SubCategoryMvpPresenter<V> {
    public SubCategoryPresenter(DataManager dataManager) {
        super(dataManager);
    }

}
