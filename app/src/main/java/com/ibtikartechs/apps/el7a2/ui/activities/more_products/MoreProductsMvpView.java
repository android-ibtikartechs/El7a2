package com.ibtikartechs.apps.el7a2.ui.activities.more_products;

import com.ibtikartechs.apps.el7a2.data.models.FooterListItemModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpView;

import java.util.ArrayList;

public interface MoreProductsMvpView extends MvpView {
    void hideErrorView();
    void showErrorView();
    String fetchErrorMessage();
    void showProgressBar();
    void addMoreToAdapter(ArrayList<FooterListItemModel> list);
}
