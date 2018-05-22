package com.ibtikartechs.apps.el7a2.ui.fragments.maindeal;

import com.ibtikartechs.apps.el7a2.ui.activities.base.MvpPresenter;

import java.util.ArrayList;

/**
 * Created by ahmedyehya on 5/3/18.
 */

public interface MainDealMvpPresenter <V extends MainDealMvpView> extends MvpPresenter<V> {
    void getMainDealData();
    void getFooterList();
    void supscribe(String email);
    void getFooter(int numOfFooter, ArrayList<Integer> currentFootersId);
}
