package com.ibtikartechs.apps.el7a2.data.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by ahmedyehya on 5/1/18.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private ArrayList<String> mFragmentListTitles = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment (Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentListTitles.add(title);
        notifyDataSetChanged();
    }
    public void removeLastFragment ()
    {
        mFragmentList.remove(2);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }


}
