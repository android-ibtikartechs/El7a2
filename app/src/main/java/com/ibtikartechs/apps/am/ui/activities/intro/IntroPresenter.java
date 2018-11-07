package com.ibtikartechs.apps.am.ui.activities.intro;

import com.ibtikartechs.apps.am.data.DataManager;
import com.ibtikartechs.apps.am.ui.activities.base.BasePresenter;

public class IntroPresenter <V extends IntroMvpView> extends BasePresenter<V> implements IntroMvpPresenter<V> {
    public IntroPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public boolean isFirstTimeLaunch() {
        return getDataManager().isFirstTimeLaunch();
    }

    @Override
    public void setFirstTimeLaunch(boolean isFirstTimeLaunch) {
        getDataManager().setFirstTimeLaunch(isFirstTimeLaunch);
    }
}
