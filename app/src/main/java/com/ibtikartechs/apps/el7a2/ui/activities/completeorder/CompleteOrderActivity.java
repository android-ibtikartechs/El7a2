package com.ibtikartechs.apps.el7a2.ui.activities.completeorder;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.adapters.ViewPagerAdapter;
import com.ibtikartechs.apps.el7a2.ui.fragments.confirmorder.ConfirmOrderFragment;
import com.ibtikartechs.apps.el7a2.ui.fragments.payment.PaymentFragment;
import com.ibtikartechs.apps.el7a2.ui.fragments.shippinginformation.ShippingInformationFragment;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;
import com.ibtikartechs.apps.el7a2.ui_utilities.NonSwipeableViewPager;

import butterknife.BindView;

public class CompleteOrderActivity extends AppCompatActivity {
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_view_pager)
    NonSwipeableViewPager viewPager;
    @BindView(R.id.main_tabLayout)
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);
        setupActionBar();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        CustomFontTextView tabOne = (CustomFontTextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_complete_order, null);
        tabOne.setText("الشحن");
        tabLayout.getTabAt(0).setCustomView(tabOne);

    }

    private void setupActionBar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_action_bar_title, null);

        ((CustomFontTextView)v.findViewById(R.id.title)).setText("نتائج البحث");
        actionBar.setCustomView(v);
    }

    public void setupViewPager(ViewPager viewPager)
    {
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(ShippingInformationFragment.newInstance("any","any"),"shipping fragment");
        viewPagerAdapter.addFragment(PaymentFragment.newInstance("any","any"),"payment fragment");
        viewPagerAdapter.addFragment(ConfirmOrderFragment.newInstance("any","any"), "confirm order fragment");

    }
}
