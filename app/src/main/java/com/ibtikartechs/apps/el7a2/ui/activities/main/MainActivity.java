package com.ibtikartechs.apps.el7a2.ui.activities.main;

import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.ibtikartechs.apps.el7a2.MvpApp;
import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.adapters.NavItemsAdapter;
import com.ibtikartechs.apps.el7a2.data.adapters.ViewPagerAdapter;
import com.ibtikartechs.apps.el7a2.data.models.NavItemModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BaseActivity;
import com.ibtikartechs.apps.el7a2.ui.fragments.CategoryFragment;
import com.ibtikartechs.apps.el7a2.ui.fragments.MainDealFragment;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomeViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView, NavItemsAdapter.NavItemClickListener {
    @BindView(R.id.main_toolbar) Toolbar toolbar;
    @BindView(R.id.lv_nav_bar) ListView nvListView;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.main_view_pager) CustomeViewPager viewPager;
    @BindView(R.id.main_tabLayout) TabLayout tabLayout;
    MainPresenter presenter;
    private ViewPagerAdapter adapter;
    private ActionBarDrawerToggle drawerToggle;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler(Looper.getMainLooper());
        ButterKnife.bind(this);
        setupActionBar();
        setupNavDrawer();
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new MainPresenter(dataManager);
        presenter.onAttach(this);


        addMainDealFragment();
        /*addCategoryFragment("1");
        addCategoryFragment("2");
        addCategoryFragment("3");
        addCategoryFragment("4");
        addCategoryFragment("5"); */

        //addCategoryTab("الصفقة الرئيسية",0);
        /*addCategoryTab("إجازات",1);
        addCategoryTab("أحدث المنتجات",2);
        addCategoryTab("منتجات أبل",3);
        addCategoryTab("منتجات أبل",4);
        addCategoryTab("منتجات أبل",5);*/


        presenter.getCategories();
    }

    private void setupViewPager() {
        viewPager.setOffscreenPageLimit(4);
        adapter = new ViewPagerAdapter (getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }




    public void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    public void setupNavDrawer() {
        if (drawerToggle == null) {
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
                public void onDrawerClosed(View view) {
                }

                public void onDrawerOpened(View drawerView) {

                }

                public void onDrawerSlide(View drawerView, float slideOffset) {
                }

                public void onDrawerStateChanged(int newState) {

                }

            };
            drawerLayout.setDrawerListener(drawerToggle);
        }

        drawerToggle.syncState();

        ArrayList<NavItemModel> arrayList = new ArrayList<>();
        arrayList.add(new NavItemModel("الصفقة الرئيسية",R.mipmap.ic_launcher));
        arrayList.add(new NavItemModel("أجازات",R.mipmap.ic_launcher));
        arrayList.add(new NavItemModel("المنتجات الأخيرة",R.mipmap.ic_launcher));
        arrayList.add(new NavItemModel("منتجات أبل",R.mipmap.ic_launcher));
        arrayList.add(new NavItemModel("طلباتي",R.mipmap.ic_launcher));
        arrayList.add(new NavItemModel("الإشعارات",R.mipmap.ic_launcher));
        arrayList.add(new NavItemModel("الإعدادات",R.mipmap.ic_launcher));
        arrayList.add(new NavItemModel("المساعدة",R.mipmap.ic_launcher));
        arrayList.add(new NavItemModel("خدمة العملاء",R.mipmap.ic_launcher));

        NavItemsAdapter itemsAdapter = new NavItemsAdapter(this,arrayList);
        itemsAdapter.setCustomButtonListner(this);
        nvListView.setAdapter(itemsAdapter);
    }

    @Override
    public void onItemClickListner(View buttonView, int position) {

    }



    @Override
    public void addCategoryTab(final String title, final int tabIndex) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                CustomFontTextView tab;
                tab = (CustomFontTextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
                tab.setText(title);
                tabLayout.getTabAt(tabIndex).setCustomView(tab);
                if(tabIndex == 0)
                    ((CustomFontTextView)tabLayout.getTabAt(0).getCustomView()).setTextColor(getResources().getColor(R.color.white));

            }
        });

    }

    @Override
    public void addCategoryFragment(final String id) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                adapter.addFragment(CategoryFragment.newInstance(id,"any"), "category");
            }
        });

    }

    @Override
    public void addMainDealFragment() {
        adapter.addFragment(MainDealFragment.newInstance("any","any"),"main deal fragment");
    }

    @Override
    public void setupCategoryTabs() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {


                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        ((CustomFontTextView) tab.getCustomView()).setTextColor(getResources().getColor(R.color.white));
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                         ((CustomFontTextView) tab.getCustomView()).setTextColor(getResources().getColor(R.color.off_white));
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

            }
        });

    }
}
