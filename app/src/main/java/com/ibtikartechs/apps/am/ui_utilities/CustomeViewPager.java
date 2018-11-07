package com.ibtikartechs.apps.am.ui_utilities;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by ahmedyehya on 5/1/18.
 */

public class CustomeViewPager extends ViewPager {
    public CustomeViewPager(Context context) {
        super(context);
    }

    public CustomeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, false);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }
}
