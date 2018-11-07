package com.ibtikartechs.apps.am.ui.activities.more_products;

import com.ibtikartechs.apps.am.data.models.FooterListItemModel;
import com.ibtikartechs.apps.am.ui.activities.base.MvpView;

import java.util.ArrayList;

public interface MoreProductsMvpView extends MvpView {
    void hideErrorView();
    void showErrorView();
    String fetchErrorMessage();
    void showProgressBar();
    void addMoreToAdapter(ArrayList<FooterListItemModel> list);
}
