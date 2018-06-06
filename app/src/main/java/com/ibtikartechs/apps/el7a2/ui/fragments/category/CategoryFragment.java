package com.ibtikartechs.apps.el7a2.ui.fragments.category;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ibtikartechs.apps.el7a2.MvpApp;
import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.adapters.ViewPagerAdapter;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BaseFragment;
import com.ibtikartechs.apps.el7a2.ui.fragments.subcategory.SubCategoryFragment;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomeViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends BaseFragment implements CategoryMvpView, SubCategoryFragment.customButtonListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.subcategories_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    CustomeViewPager viewPager;
    @BindView(R.id.main_progress)
    ProgressBar mainProgressBar;
    @BindView(R.id.error_btn_retry)
    Button btnRetry;
    @BindView(R.id.lout_category_data_layout)
    ConstraintLayout loutData;
    @BindView(R.id.tv_main_deal_error_txt_cause)
    CustomFontTextView teexErrorCause;
    @BindView(R.id.lout_main_deal_error_layout)
    LinearLayout loutError;
    @BindView(R.id.error_layout)
    CardView loutMainError;
    customButtonListener customListener;

    // TODO: Rename and change types of parameters
    private String categoryId;
    private String mParam2;
    private TextView tvId;
    private ViewPagerAdapter adapter;
    Handler handler;
    CategoryPresenter presenter;


    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2, customButtonListener customListener) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        fragment.customListener = customListener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());
        DataManager dataManager = ((MvpApp) getActivity().getApplication()).getDataManager();
        presenter = new CategoryPresenter(dataManager);
        presenter.onAttach(this);
        if (getArguments() != null) {
            categoryId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mainProgressBar != null) {
            mainProgressBar.setIndeterminate(true);
            mainProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
            mainProgressBar.setVisibility(View.VISIBLE);
        }
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getSubCategories(categoryId);
            }
        });



        presenter.getSubCategories(categoryId);
    }

    private void setupViewPager() {
        viewPager.setOffscreenPageLimit(2);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void hideErrorView() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                loutMainError.setVisibility(View.GONE);
                loutData.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void showErrorView() {
        handler.post(new Runnable() {
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
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void showProgressBar() {
        loutError.setVisibility(View.GONE);
        mainProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void addSubCategoryTab(final String title,final int tabIndex) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                CustomFontTextView tab;
                tab = (CustomFontTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_subcategory, null);
                tab.setText(title);
                tabLayout.getTabAt(tabIndex).setCustomView(tab);
                if(tabIndex == 0)
                    ((CustomFontTextView)tabLayout.getTabAt(0).getCustomView()).setTextColor(getResources().getColor(R.color.white));

            }
        });
    }

    @Override
    public void addSubCategoryFragment(final String id) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.addFragment(SubCategoryFragment.newInstance(id,"any",CategoryFragment.this), "category");
            }
        });
    }

    @Override
    public void setupSubCategoryTabs() {
        handler.post(new Runnable() {
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
                        ((CustomFontTextView) tab.getCustomView()).setTextColor(getResources().getColor(R.color.colorUnSelectedTabSubCat));
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((SubCategoryFragment) adapter.getItem(position)).onResume();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onItemCartChange() {
        customListener.onItemCartChange();
    }

    public interface customButtonListener {
        public void onItemCartChange();
    }
    public void setCustomButtonListner(customButtonListener listener) {
        this.customListener = listener;
    }
}
