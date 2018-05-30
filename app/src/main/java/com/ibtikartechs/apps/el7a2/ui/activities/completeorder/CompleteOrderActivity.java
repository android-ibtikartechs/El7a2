package com.ibtikartechs.apps.el7a2.ui.activities.completeorder;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.adapters.ViewPagerAdapter;
import com.ibtikartechs.apps.el7a2.data.models.AddressModel;
import com.ibtikartechs.apps.el7a2.ui.fragments.confirmorder.ConfirmOrderFragment;
import com.ibtikartechs.apps.el7a2.ui.fragments.payment.PaymentFragment;
import com.ibtikartechs.apps.el7a2.ui.fragments.shippinginformation.ShippingInformationFragment;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;
import com.ibtikartechs.apps.el7a2.ui_utilities.NonSwipeableViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompleteOrderActivity extends AppCompatActivity implements ShippingInformationFragment.OnNextStepListener {
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
        ButterKnife.bind(this);

        setupActionBar();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        CustomFontTextView tabOne = (CustomFontTextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_complete_order, null);
        tabOne.setText("الشحن");
        tabOne.setTextColor(getResources().getColor(R.color.white_blue));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        CustomFontTextView tabTwo = (CustomFontTextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_complete_order, null);
        tabTwo.setText("الدفع");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        CustomFontTextView tabThree = (CustomFontTextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_complete_order,null);
        tabThree.setText("تأكيد");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        for (int i = 0; i < 3; i++) {
            ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i).setEnabled(false);
        }



        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
             View view =  ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                ((CustomFontTextView) view.findViewById(R.id.tab)).setTextColor(getResources().getColor(R.color.white_blue));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

        ((CustomFontTextView)v.findViewById(R.id.title)).setText("متابعة الشراء");
        actionBar.setCustomView(v);
    }

    public void setupViewPager(ViewPager viewPager)
    {
        viewPager.setOffscreenPageLimit(3);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.addFragment(ShippingInformationFragment.newInstance("any","any"),"shipping fragment");
        viewPagerAdapter.addFragment(PaymentFragment.newInstance("any","any"),"payment fragment");
        viewPagerAdapter.addFragment(ConfirmOrderFragment.newInstance("any","any"), "confirm order fragment");

    }

    @Override
    public void onNextStepclicked(AddressModel addressModel) {
        PaymentFragment paymentFragment = (PaymentFragment)
                viewPagerAdapter.getItem(1);

        if(paymentFragment != null)
        {
            paymentFragment.pushData(addressModel);
            viewPager.setCurrentItem(1);

            View view =  ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0);
            ((CustomFontTextView) view.findViewById(R.id.tab)).setCompoundDrawablesWithIntrinsicBounds(0,R.mipmap.ic_action_check,0,0);
        }


    }

    public  static Intent getStartIntent(Context context)
    {
        Intent intent = new Intent(context,CompleteOrderActivity.class);
        return intent;
    }
}
