package com.ibtikartechs.apps.am.ui.activities.main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ibtikartechs.apps.am.MvpApp;
import com.ibtikartechs.apps.am.R;
import com.ibtikartechs.apps.am.data.DataManager;
import com.ibtikartechs.apps.am.data.adapters.NavItemsAdapter;
import com.ibtikartechs.apps.am.data.adapters.ViewPagerAdapter;
import com.ibtikartechs.apps.am.data.models.NavItemModel;
import com.ibtikartechs.apps.am.ui.activities.MyOrdersActivity;
import com.ibtikartechs.apps.am.ui.activities.base.BaseActivity;
import com.ibtikartechs.apps.am.ui.activities.registeration.RegisterationActivity;
import com.ibtikartechs.apps.am.ui.activities.shopping_cart.ShoppingCartActivity;
import com.ibtikartechs.apps.am.ui.fragments.category.CategoryFragment;
import com.ibtikartechs.apps.am.ui.fragments.maindeal.MainDealFragment;
import com.ibtikartechs.apps.am.ui.fragments.searchdialogfragment.SearchDialogFragment;
import com.ibtikartechs.apps.am.ui_utilities.CustomFontTextView;
import com.ibtikartechs.apps.am.ui_utilities.CustomeViewPager;
import com.ibtikartechs.apps.am.ui_utilities.DialogFragmentActivationLink;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity implements MainMvpView, NavItemsAdapter.NavItemClickListener, CategoryFragment.customButtonListener {
    @BindView(R.id.main_toolbar) Toolbar toolbar;
    @BindView(R.id.lv_nav_bar) ListView nvListView;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.main_view_pager) CustomeViewPager viewPager;
    @BindView(R.id.main_tabLayout) TabLayout tabLayout;
    @BindView(R.id.lout_main_deal_error_layout)
    LinearLayout loutError;
    @BindView(R.id.error_layout)
    CardView loutMainError;
    @BindView(R.id.main_progress)
    ProgressBar mainProgressBar;
    @BindView(R.id.tv_main_deal_error_txt_cause)
    CustomFontTextView teexErrorCause;
    @BindView(R.id.error_btn_retry)
    Button btnRetry;
    @BindView(R.id.lout_login)
    LinearLayout loutLogin;
    @BindView(R.id.tv_user_name)
    CustomFontTextView tvEmailDisplay;
    @BindView(R.id.tv_btn_logout)
    CustomFontTextView btnLogout;
    MainPresenter presenter;
    private ViewPagerAdapter adapter;
    private ActionBarDrawerToggle drawerToggle;
    private Handler mHandler;
    Menu menu;
    TextView textCartItemCount;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler(Looper.getMainLooper());
        ButterKnife.bind(this);

        if (mainProgressBar != null) {
            mainProgressBar.setIndeterminate(true);
            mainProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
            mainProgressBar.setVisibility(View.VISIBLE);
        }
        setupActionBar();
        setupNavDrawer();
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new MainPresenter(dataManager);
        presenter.onAttach(this);


        checkLoginStatus();

        // addMainDealFragment();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.logout();

            }
        });
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getCategories();
            }
        });
        presenter.getCategories();



        loutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                startActivity(RegisterationActivity.getStartIntent(MainActivity.this));
            }
        });

        // Log.e("Token is ", FirebaseInstanceId.getInstance().getToken());
    }

    private void checkLoginStatus() {
        if (presenter.getUserEmail() != null) {
            tvEmailDisplay.setText(presenter.getUserEmail());
            btnLogout.setVisibility(View.VISIBLE);
        }
        else
        {
            tvEmailDisplay.setText("تسجيل / تسجيل الدخول");
            btnLogout.setVisibility(View.GONE);
        }

    }

    private void setupViewPager() {
        viewPager.setOffscreenPageLimit(4);
        adapter = new ViewPagerAdapter (getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        final MenuItem cartItem = menu.findItem(R.id.action_cart);
        View actionView = MenuItemCompat.getActionView(cartItem);
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
        setupBadge();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(cartItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_cart :
                startActivity(ShoppingCartActivity.getStartIntent(this));
               /* FragmentManager fm = getSupportFragmentManager();
                SearchDialogFragment searchDialogFragment = new SearchDialogFragment();
                searchDialogFragment.show(fm, "alert"); */
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setupBadge() {

       /* if (textCartItemCount != null) {
            textCartItemCount.setText(String.valueOf(Math.min(presenter.getNumberOfItemsInCart(), 99)));
        } */

        if (textCartItemCount != null) {
       Disposable disposable = presenter.getDataManager().getCartItemsNumber().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            textCartItemCount.setText(String.valueOf(Math.min(integer, 99)));
                        }
                    });
       compositeDisposable.add(disposable);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupBadge();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    public void setupActionBar() {

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        changeLang(MainActivity.this,"ar");
    }

    public void setupNavDrawer() {
        if (drawerToggle == null) {
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
                public void onDrawerClosed(View view) {
                }

                public void onDrawerOpened(View drawerView) {

                    checkLoginStatus();
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
        arrayList.add(new NavItemModel("طلباتي",R.mipmap.ic_launcher));
        //arrayList.add(new NavItemModel("الإشعارات",R.mipmap.ic_launcher));
        //arrayList.add(new NavItemModel("الإعدادات",R.mipmap.ic_launcher));
        //arrayList.add(new NavItemModel("المساعدة",R.mipmap.ic_launcher));
        //arrayList.add(new NavItemModel("خدمة العملاء",R.mipmap.ic_launcher));

        NavItemsAdapter itemsAdapter = new NavItemsAdapter(this,arrayList);
        itemsAdapter.setCustomButtonListner(this);
        nvListView.setAdapter(itemsAdapter);
    }

    @Override
    public void onItemClickListner(View buttonView, int position) {
        switch (position)
        {
            case 0 :
                drawerLayout.closeDrawers();
                startActivity(new Intent(this, MyOrdersActivity.class));
                break;
        }
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
                adapter.addFragment(CategoryFragment.newInstance(id,"any", MainActivity.this), "category");
            }
        });

    }

    @Override
    public void addMainDealFragment() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                adapter.addFragment(MainDealFragment.newInstance("any","any"),"main deal fragment");
            }
        });
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

    @Override
    public void hideErrorView() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                loutMainError.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showErrorView() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mainProgressBar.setVisibility(View.GONE);
                loutError.setVisibility(View.VISIBLE);
                teexErrorCause.setText(fetchErrorMessage());
            }
        });

    }

    @Override
    public String fetchErrorMessage() {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);
        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        }
        return errorMsg;
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void showProgressBar() {
        loutError.setVisibility(View.GONE);
        mainProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeDrawer() {
        drawerLayout.closeDrawers();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public void onItemCartChange() {
        setupBadge();
    }
}
