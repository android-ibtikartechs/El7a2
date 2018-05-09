package com.ibtikartechs.apps.el7a2.ui.fragments.maindeal;


import android.content.Context;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ibtikartechs.apps.el7a2.MvpApp;
import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.adapters.FooterListAdapter;
import com.ibtikartechs.apps.el7a2.data.models.FooterListItemModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BaseFragment;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainDealFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainDealFragment extends BaseFragment implements MainDealMvpView, FooterListAdapter.ContainerClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Handler handler;
    MainDealPresenter presenter;
    private FooterListAdapter firstListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<FooterListItemModel> firstArrayList;
    private Runnable runnable;



    @BindView(R.id.tv_main_deal_days)
    CustomFontTextView tvDays;
    @BindView(R.id.rv_main_deal_suggested_category)
    RecyclerView rvFirstSuggCategory;
    @BindView(R.id.tv_main_deal_hours)
    CustomFontTextView tvHours;
    @BindView(R.id.tv_main_deal_minutes)
    CustomFontTextView tvMinutes;
    @BindView(R.id.progress_bar_footer)
    ProgressBar progBarFirstFooter;
    @BindView(R.id.tv_main_deal_seconds)
    CustomFontTextView tvSeconds;
    @BindView(R.id.tv_main_deal_old_price)
    TextView tvOldPrice;
    @BindView(R.id.lout_main_deal_error_layout)
    LinearLayout loutError;
    @BindView(R.id.error_layout)
    CardView loutMainError;
    @BindView(R.id.lout_main_deal_data_layout)
    ConstraintLayout loutData;
    @BindView(R.id.im_main_deal_main_sale)
    ImageView imMainSale;
    @BindView(R.id.tv_main_deal_name_sale)
    TextView tvMainSaleName;
    @BindView(R.id.tv_main_deal_main_price)
    TextView tvMainSalePrice;
    @BindView(R.id.im_main_deal_first_sale)
    ImageView imFirstSale;
    @BindView(R.id.im_main_deal_second_sale)
    ImageView imSecondSale;
    @BindView(R.id.tv_main_deal_first_sale_price)
    CustomFontTextView tvFirstSalePrice;
    @BindView(R.id.tv_main_deal_second_sale_price)
    CustomFontTextView tvSecondSalePrice;
    @BindView(R.id.main_progress)
    ProgressBar mainProgressBar;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.tv_main_deal_error_txt_cause)
    CustomFontTextView teexErrorCause;
    @BindView(R.id.error_btn_retry)
    Button btnRetry;

    public MainDealFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainDealFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainDealFragment newInstance(String param1, String param2) {
        MainDealFragment fragment = new MainDealFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());
        DataManager dataManager = ((MvpApp) getActivity().getApplication()).getDataManager();
        presenter = new MainDealPresenter(dataManager);
        presenter.onAttach(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_deal, container, false);
        ButterKnife.bind(this, rootView);
        firstArrayList = new ArrayList<>();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getMainDealData();
        if (mainProgressBar != null) {
            mainProgressBar.setIndeterminate(true);
            mainProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
            mainProgressBar.setVisibility(View.VISIBLE);
        }

        if (progBarFirstFooter != null) {
            progBarFirstFooter.setIndeterminate(true);
            progBarFirstFooter.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
            progBarFirstFooter.setVisibility(View.VISIBLE);
        }

        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        rvFirstSuggCategory.setLayoutManager(linearLayoutManager);
        rvFirstSuggCategory.setHasFixedSize(true);

        rvFirstSuggCategory.setItemViewCacheSize(20);
        rvFirstSuggCategory.setDrawingCacheEnabled(true);
        rvFirstSuggCategory.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        populatRecyclerView();
        tvOldPrice.setPaintFlags(tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getMainDealData();
            }
        });
        //countDownStart("2018-05-10");
    }

    private void populatRecyclerView() {
        firstListAdapter = new FooterListAdapter(getActivity(), firstArrayList);
        firstListAdapter.setCustomButtonListner(this);
        rvFirstSuggCategory.setAdapter(firstListAdapter);
        firstListAdapter.notifyDataSetChanged();
    }

    public void countDownStart(final String futureTime) {
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
// Please here set your event date//YYYY-MM-DD

                    Date futureDate = dateFormat.parse(futureTime);
                    Date currentDate = new Date();
                    //Date currentDate = startDateFormat.parse(startTime);

                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;

                        tvDays.setText("" + String.format("%02d", days));
                        tvHours.setText("" + String.format("%02d", hours));
                        tvMinutes.setText(""
                                + String.format("%02d", minutes));
                        tvSeconds.setText(""
                                + String.format("%02d", seconds));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);


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
    public void populateData(final String productImgUrl, final String productName, final String productPrice, final String endDate, final String firstSaleImgUrl,final String secondSaleImgUrl, final String details) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(getActivity())
                        .load(productImgUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder)
                        .into(imMainSale);

                tvMainSaleName.setText(productName);

                tvMainSalePrice.setText(productPrice);

                countDownStart(endDate);

                Glide.with(getActivity())
                        .load(firstSaleImgUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder)
                        .into(imFirstSale);


                Glide.with(getActivity())
                        .load(secondSaleImgUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder)
                        .into(imSecondSale);

                mWebView.loadDataWithBaseURL("", details, "text/html", "utf-8", "");

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

    @Override
    public void showProgressBar() {
        loutError.setVisibility(View.GONE);
        mainProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFooterProgressBar() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                progBarFirstFooter.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void addMoreToAdapter(final ArrayList<FooterListItemModel> list) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                firstListAdapter.addAll(list);
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onItemClickListener(String id) {
        Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
    }
}
