package com.ibtikartechs.apps.am.ui.fragments.subcategory;

import com.ibtikartechs.apps.am.data.models.FooterListItemModel;
import com.ibtikartechs.apps.am.ui.activities.base.MvpView;

import java.util.ArrayList;

/**
 * Created by ahmedyehya on 5/9/18.
 */

public interface SubCategoryMvpView extends MvpView {
    void hideErrorView();
    void showErrorView();
    String fetchErrorMessage();
    void showProgressBar();
    void setLastPageTrue();
    void addMoreToAdapter(final ArrayList<FooterListItemModel> list);
    void addLoadingFooter();
    void removeLoadingFooter();
    void showRetryAdapter();
    void setIsLoadingFalse();

}
