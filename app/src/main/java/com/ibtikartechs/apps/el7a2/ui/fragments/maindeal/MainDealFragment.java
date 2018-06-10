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
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ibtikartechs.apps.el7a2.MvpApp;
import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.StaticValues;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.adapters.FooterListAdapter;
import com.ibtikartechs.apps.el7a2.data.models.FooterListItemModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BaseFragment;
import com.ibtikartechs.apps.el7a2.ui.activities.main_deal_deatails.MainDealDetailsActivity;
import com.ibtikartechs.apps.el7a2.ui.activities.more_products.MoreProductsActivity;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.square1.richtextlib.ui.RichContentView;
import io.square1.richtextlib.v2.RichTextV2;
import io.square1.richtextlib.v2.content.RichTextDocumentElement;

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
    private FooterListAdapter secondListAdapter;
    private FooterListAdapter thirdListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<FooterListItemModel> firstArrayList;
    private ArrayList<FooterListItemModel> secondArrayList;
    private ArrayList<FooterListItemModel> thirdArrayList;
    private Runnable runnable;



    @BindView(R.id.tv_main_deal_days)
    CustomFontTextView tvSeconds;
    @BindView(R.id.lout_supplements_container)
    ConstraintLayout loutSupplementsContainer;
    @BindView(R.id.rv_main_deal_suggested_category)
    RecyclerView rvFirstSuggCategory;
    @BindView(R.id.tv_main_deal_hours)
    CustomFontTextView tvMinutes;
    @BindView(R.id.tv_main_deal_minutes)
    CustomFontTextView tvHours;
    @BindView(R.id.tv_main_deal_seconds)
    CustomFontTextView tvDays;
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
    CustomFontTextView tvMainSaleName;
    @BindView(R.id.tv_main_deal_main_price)
    TextView tvMainSalePrice;
    @BindView(R.id.im_main_deal_first_sale)
    ImageView imFirstSale;
    @BindView(R.id.im_main_deal_second_sale)
    ImageView imSecondSale;
    @BindView(R.id.main_progress)
    ProgressBar mainProgressBar;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.tv_main_deal_error_txt_cause)
    CustomFontTextView teexErrorCause;
    @BindView(R.id.error_btn_retry)
    Button btnRetry;
    @BindView(R.id.tv_main_deal_discount_parcent)
    TextView tvDiscountPercent;
    @BindView(R.id.btn_main_deal_buy_now)
    Button btnBuy;
    @BindView(R.id.lout_some_header)
    ConstraintLayout loutSomeHeader;
    @BindView(R.id.et_main_deal_email)
    EditText etEmailNewsLater;
    @BindView(R.id.btn_main_deal_subscribe)
    Button btnSupscribe;
    @BindView(R.id.cardView4)
    CardView loutFooterCat1;
    @BindView(R.id.cardView20)
    CardView loutFooterCat2;
    @BindView(R.id.cardView30)
    CardView loutFooterCat3;
    @BindView(R.id.im_main_deal_banner)
    ImageView imBanner1;
    @BindView(R.id.im_main_deal_banner_2)
    ImageView imBanner2;
    @BindView(R.id.tv_main_deal_suggested_category)CustomFontTextView tvNameFooterCat1;
    @BindView(R.id.tv_btn_main_deal_more) CustomFontTextView btnMoreFooterCat1;
    @BindView(R.id.tv_main_deal_suggested_category_2)CustomFontTextView tvNameFooterCat2;
    @BindView(R.id.tv_btn_main_deal_more_2) CustomFontTextView btnMoreFooterCat2;
    @BindView(R.id.tv_main_deal_suggested_category_3)CustomFontTextView tvNameFooterCat3;
    @BindView(R.id.tv_btn_main_deal_more_3) CustomFontTextView btnMoreFooterCat3;
    @BindView(R.id.rv_main_deal_suggested_category_3) RecyclerView rvFooter3;
    @BindView(R.id.rv_main_deal_suggested_category_2) RecyclerView rvFooter2;
    String idFooter1,idFooter2,idFooter3;
    String footer1Name, footer2Name, footer3Name;
    ArrayList<Integer> footerIdsList;
    String dealId;
    String firstBannerId, secondBannerId;
    int numOfFooters;
    String product_Name;

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
        footerIdsList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_deal, container, false);
        ButterKnife.bind(this, rootView);
        firstArrayList = new ArrayList<>();
        secondArrayList = new ArrayList<>();
        thirdArrayList = new ArrayList<>();
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



        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        rvFirstSuggCategory.setLayoutManager(linearLayoutManager);
        rvFooter2.setLayoutManager(linearLayoutManager2);
        rvFooter3.setLayoutManager(linearLayoutManager3);

        rvFirstSuggCategory.setHasFixedSize(true);
        rvFooter2.setHasFixedSize(true);


        rvFirstSuggCategory.setItemViewCacheSize(20);
        rvFirstSuggCategory.setDrawingCacheEnabled(true);
        rvFirstSuggCategory.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        rvFooter2.setItemViewCacheSize(20);
        rvFooter2.setDrawingCacheEnabled(true);
        rvFooter2.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        rvFooter3.setItemViewCacheSize(20);
        rvFooter3.setDrawingCacheEnabled(true);
        rvFooter3.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        populatRecyclerView();
        tvOldPrice.setPaintFlags(tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getMainDealData();
            }
        });
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainDealDetailsActivity.getStartIntent(getActivity(), dealId, StaticValues.DEAL_FLAG, product_Name));
            }
        });
        imMainSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainDealDetailsActivity.getStartIntent(getActivity(), dealId, StaticValues.DEAL_FLAG, product_Name));
            }
        });
        loutSupplementsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainDealDetailsActivity.getStartIntent(getActivity(), dealId, StaticValues.DEAL_FLAG, product_Name));
            }
        });

        loutSomeHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainDealDetailsActivity.getStartIntent(getActivity(), dealId, StaticValues.DEAL_FLAG, product_Name));
            }
        });

        btnSupscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailToCheck = etEmailNewsLater.getText().toString();
                if (!emailToCheck.isEmpty())
                {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailToCheck).matches())
                    {
                        presenter.supscribe(emailToCheck);
                    }
                }
                else
                    showToast("قم بإدخال البريد الإكتروني أولا");
            }
        });
        //countDownStart("2018-05-10");
        btnMoreFooterCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(MoreProductsActivity.getStartIntent(getActivity(),idFooter1, footer1Name));
            }
        });

        btnMoreFooterCat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(MoreProductsActivity.getStartIntent(getActivity(),idFooter2, footer2Name));
            }
        });

        btnMoreFooterCat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(MoreProductsActivity.getStartIntent(getActivity(),idFooter3, footer3Name));
            }
        });
    }

    private void populatRecyclerView() {
        firstListAdapter = new FooterListAdapter(getActivity(), firstArrayList);
        secondListAdapter = new FooterListAdapter(getActivity(), secondArrayList);
        thirdListAdapter = new FooterListAdapter(getActivity(), thirdArrayList);
        firstListAdapter.setCustomButtonListner(this);
        secondListAdapter.setCustomButtonListner(this);
        thirdListAdapter.setCustomButtonListner(this);
        rvFirstSuggCategory.setAdapter(firstListAdapter);
        rvFooter2.setAdapter(secondListAdapter);
        rvFooter3.setAdapter(thirdListAdapter);
        firstListAdapter.notifyDataSetChanged();
        secondListAdapter.notifyDataSetChanged();
        thirdListAdapter.notifyDataSetChanged();



    }

    public void countDownStart(final String futureTime) {
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
    public void populateData(final String productImgUrl, final String productName, final String productPrice, final String endDate, final String firstSaleImgUrl, final String secondSaleImgUrl, final String details, final String oldPrice, final String discountPercent, final int numOfFooters, final String firstBannerImgUrl, final String secondBannerImgUrl, final String firstBannerId, final String secondBannerId) {
        product_Name = productName;
        presenter.getFooter(numOfFooters, footerIdsList);
        this.firstBannerId = firstBannerId;
        this.secondBannerId = secondBannerId;
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


                Glide.with(getActivity())
                        .load(firstBannerImgUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder)
                        .into(imBanner1);

                imBanner1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(MainDealDetailsActivity.getStartIntent(getActivity(),firstBannerId,StaticValues.PROD_FLAG,"منتجات"));
                    }
                });

                Glide.with(getActivity())
                        .load(secondBannerImgUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder)
                        .into(imBanner2);

                imBanner2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(MainDealDetailsActivity.getStartIntent(getActivity(),secondBannerId,StaticValues.PROD_FLAG,"منتجات"));
                    }
                });

                mWebView.setWebViewClient(new WebViewClient() {

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        // TODO Auto-generated method stub
                        view.loadUrl(url);
                        return true;
                    }
                });


                mWebView.loadDataWithBaseURL("", buildHtml(details), "text/html", "utf-8", "");

          /*      RichTextDocumentElement contents = RichTextV2.textFromHtml(getActivity(), details);
                mWebView.setText(contents); */

                tvDiscountPercent.setText(discountPercent);
                tvOldPrice.setText(oldPrice);




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

    @Override
    public void addMoreToAdapter2Footer(final ArrayList<FooterListItemModel> list) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                secondListAdapter.addAll(list);
            }
        });
    }

    @Override
    public void addMoreToAdapter3Footer(final ArrayList<FooterListItemModel> list) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                thirdListAdapter.addAll(list);
            }
        });
    }


    @Override
    public void setDealId(final String id) {
        dealId = id;
    }

    @Override
    public void setNumofFooters(int num) {
        this.numOfFooters = num;
    }

    @Override
    public void showToast(final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void showFooter1(final String catName, final ArrayList<FooterListItemModel> productsList, final String catId) {
        if (!(footerIdsList.contains(Integer.valueOf(catId)))) {
            addMoreToAdapter(productsList);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    loutFooterCat1.setVisibility(View.VISIBLE);
                    tvNameFooterCat1.setText(catName);
                    idFooter1 = catId;
                    footerIdsList.add(Integer.valueOf(catId));
                    presenter.getFooter(numOfFooters, footerIdsList);

                }
            });
            footer1Name = catName;
        }
        else if (footerIdsList.size()<numOfFooters)
            presenter.getFooter(numOfFooters,footerIdsList);
    }

    @Override
    public void showFooter2(final String catName, final ArrayList<FooterListItemModel> productsList, final String catId) {
        if (!(footerIdsList.contains(Integer.valueOf(catId)))) {
            addMoreToAdapter2Footer(productsList);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    loutFooterCat2.setVisibility(View.VISIBLE);
                    tvNameFooterCat2.setText(catName);
                    idFooter2 = catId;
                    footerIdsList.add(Integer.valueOf(catId));
                    footer2Name = catName;
                    if (numOfFooters == 3)
                        presenter.getFooter(numOfFooters, footerIdsList);
                }
            });
        }
        else if (footerIdsList.size()<numOfFooters)
        presenter.getFooter(numOfFooters,footerIdsList);
    }

    @Override
    public void showFooter3(final String catName, final ArrayList<FooterListItemModel> productsList, final String catId) {
        if (!(footerIdsList.contains(Integer.valueOf(catId)))) {
            addMoreToAdapter3Footer(productsList);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    loutFooterCat3.setVisibility(View.VISIBLE);
                    tvNameFooterCat3.setText(catName);
                    idFooter3 = catId;
                    footerIdsList.add(Integer.valueOf(catId));
                    footer3Name = catName;
                }
            });
        }
        else if (footerIdsList.size()<numOfFooters)
            presenter.getFooter(numOfFooters,footerIdsList);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onItemClickListener(String id, String title) {
        startActivity(MainDealDetailsActivity.getStartIntent(getActivity(),id,StaticValues.PROD_FLAG, title));
    }

    private String buildHtml (String text)
    {
        String resultHtml = "<html dir=\"rtl\" lang=\"ar\">\n" +
                "  <head>\n" +
                "    <link rel=\"stylesheet\"\n" +
                "          href=\"https://fonts.googleapis.com/css?family=Cairo\">\n" +
                "    <style>\n" +
                "      body {\n" +
                "        font-family: 'Cairo', sans-serif;\n" +

                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body bgcolor=\"#ffffff\">\n" +
                "    <div>" + text + "</div>\n" +
                "  </body>\n" +
                "</html>";
        return resultHtml;
    }

}
