package com.ibtikartechs.apps.el7a2.ui.activities.intro;

import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

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
