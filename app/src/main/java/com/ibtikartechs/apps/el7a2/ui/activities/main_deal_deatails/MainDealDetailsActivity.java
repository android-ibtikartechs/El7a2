package com.ibtikartechs.apps.el7a2.ui.activities.main_deal_deatails;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ibtikartechs.apps.el7a2.MvpApp;
import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.adapters.FooterListAdapter;
import com.ibtikartechs.apps.el7a2.data.models.FooterListItemModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BaseActivity;
import com.ibtikartechs.apps.el7a2.ui.fragments.maindeal.MainDealPresenter;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.square1.richtextlib.ui.RichContentView;
import io.square1.richtextlib.v2.RichTextV2;
import io.square1.richtextlib.v2.content.RichTextDocumentElement;

public class MainDealDetailsActivity extends BaseActivity implements MainDealDetailsMvpView, FooterListAdapter.ContainerClickListener {
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.customFontTextView9)
    CustomFontTextView tvDealName;
    @BindView(R.id.im_main_deal_disc_image)
    ImageView imDealImage;
    @BindView(R.id.tv_main_deal_discount_parcent)
    TextView tvDiscountPercent;
    @BindView(R.id.tv_main_deal_old_price)
    TextView tvOldPrice;
    @BindView(R.id.tv_main_deal_main_price)
    TextView tvMainPrice;
    @BindView(R.id.imageView)
    ImageView btnShare;
    @BindView(R.id.im_btn_main_deal_disc_like_button)
    ImageView btnLike;
    @BindView(R.id.lout_supplements_container)
    ConstraintLayout loutSupplements;
    @BindView(R.id.im_main_deal_first_sale)
    ImageView imFirstSupplement;
    @BindView(R.id.tv_main_deal_first_sale_price)
    CustomFontTextView tvPriceFirstSupplement;
    @BindView(R.id.im_main_deal_second_sale)
    ImageView imSecondSupplement;
    @BindView(R.id.tv_main_deal_second_sale_price)
    CustomFontTextView tvPriceSecondSupplement;
    @BindView(R.id.customFontTextView12)
    RichContentView tvMainDescription;
    @BindView(R.id.lout_main_deal_desc_timer)
    ConstraintLayout loutTimer;
    @BindView(R.id.button)
    Button btnBuy;
    @BindView(R.id.tv_main_deal_desc_timer_alternative_text)
    CustomFontTextView tvTimerAlternativeText;
    @BindView(R.id.imageView6)
    ImageView imBanner;
    @BindView(R.id.lout_main_deal_desc_suggested_category_error)
    LinearLayout loutErrorSuggCategory;

    @BindView(R.id.tv_main_deal_desc_suggested_category_error_cause)
    CustomFontTextView tvErrorCauseSuggCategory;

    @BindView(R.id.btn_main_deal_desc_suggested_category_retry)
    Button btnRetrySuggCategory;

    @BindView(R.id.tv_main_deal_desc_suggested_category_name)
    CustomFontTextView btntvSuggCategoryName;

    @BindView(R.id.tv_btn_main_deal_desc_suggested_category_more)
    CustomFontTextView btnMoreSuggCategory;

    @BindView(R.id.rv_main_deal_suggested_category_products)
    RecyclerView rvSuggCategoryProducts;

    @BindView(R.id.progress_bar_main_deal_suggested_category)
    ProgressBar progressBarSugCategory;

    @BindView(R.id.im_btn_main_deal_desc_features_drop_down)
    ImageView btnFeaturesDropDown;
    @BindView(R.id.tv_main_deal_desc_features)
    RichContentView tvFeatures;
    @BindView(R.id.im_btn_main_deal_desc_content_drop_down)
    ImageView btnContentDropDown;

    @BindView(R.id.tv_main_deal_desc_content)
    RichContentView tvContent;

    @BindView(R.id.tv_main_deal_days)
    CustomFontTextView tvDays;
    @BindView(R.id.tv_main_deal_hours)
    CustomFontTextView tvHours;
    @BindView(R.id.tv_main_deal_minutes)
    CustomFontTextView tvMinutes;
    @BindView(R.id.tv_main_deal_seconds)
    CustomFontTextView tvSeconds;

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
    @BindView(R.id.lout_features_content)
    CardView loutFeaturesContent;

    private Handler mHandler;
    private Handler handler;
    MainDealDetailsPresenter presenter;
    private FooterListAdapter firstListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<FooterListItemModel> firstArrayList;
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_deal_details);
        mHandler = new Handler(Looper.getMainLooper());
        ButterKnife.bind(this);
        setupActionBar();

        if (mainProgressBar != null) {
            mainProgressBar.setIndeterminate(true);
            mainProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
            mainProgressBar.setVisibility(View.VISIBLE);
        }

        if (progressBarSugCategory != null){
            progressBarSugCategory.setIndeterminate(true);
            progressBarSugCategory.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
            progressBarSugCategory.setVisibility(View.VISIBLE);
        }


        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new MainDealDetailsPresenter(dataManager);
        presenter.onAttach(this);

        firstArrayList = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        rvSuggCategoryProducts.setLayoutManager(linearLayoutManager);
        rvSuggCategoryProducts.setHasFixedSize(true);

        rvSuggCategoryProducts.setItemViewCacheSize(20);
        rvSuggCategoryProducts.setDrawingCacheEnabled(true);
        rvSuggCategoryProducts.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        populatRecyclerView();

        btnFeaturesDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvFeatures.getVisibility() == View.GONE)
                    tvFeatures.setVisibility(View.VISIBLE);
                else
                    tvFeatures.setVisibility(View.GONE);
            }
        });

        btnContentDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvContent.getVisibility() == View.GONE)
                    tvContent.setVisibility(View.VISIBLE);
                else
                    tvContent.setVisibility(View.GONE);
            }
        });

        hideErrorView();
    }

    private void populatRecyclerView() {
        firstListAdapter = new FooterListAdapter(this, firstArrayList);
        firstListAdapter.setCustomButtonListner(this);
        rvSuggCategoryProducts.setAdapter(firstListAdapter);
        firstListAdapter.notifyDataSetChanged();
    }

    public void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_action_bar_title, null);

        ((CustomFontTextView)v.findViewById(R.id.title)).setText("الصفقة الرئيسية");

        actionBar.setCustomView(v);

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
    public void populateData(final String dealName, final String mainImageUrl,
                             final String price, final String oldPrice,
                             final String discountPercent, final String mainDescription, final String imgUrlBanner) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                tvDealName.setText(dealName);
                if (mainImageUrl.equals("")|| mainImageUrl == null)
                    Glide.with(MainDealDetailsActivity.this)
                            .load(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imDealImage);
                else
                    Glide.with(MainDealDetailsActivity.this)
                            .load(mainImageUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.placeholder)
                            .into(imDealImage);

                if (imgUrlBanner.equals("")|| imgUrlBanner == null)
                    Glide.with(MainDealDetailsActivity.this)
                            .load(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imBanner);
                else
                    Glide.with(MainDealDetailsActivity.this)
                            .load(imgUrlBanner).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.placeholder)
                            .into(imBanner);

                tvMainPrice.setText(price);
                tvOldPrice.setText(oldPrice);
                tvDiscountPercent.setText(discountPercent);

                RichTextDocumentElement description = RichTextV2.textFromHtml(MainDealDetailsActivity.this, mainDescription);
                tvMainDescription.setText(description);

            }
        });
    }

    @Override
    public void addMoreToAdapter(final ArrayList<FooterListItemModel> list) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                firstListAdapter.addAll(list);
            }
        });
    }

    @Override
    public void hideFooterProgressBar() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressBarSugCategory.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showFooterProgressBar() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                loutErrorSuggCategory.setVisibility(View.GONE);
                progressBarSugCategory.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void showFooterErrorView() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressBarSugCategory.setVisibility(View.GONE);
                loutErrorSuggCategory.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void hideFooterErrorView() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                loutErrorSuggCategory.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void setTimer(final String time) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                loutTimer.setVisibility(View.VISIBLE);
                countDownStart(time);
            }
        });

    }

    @Override
    public void setSupplement(String price1, String price2, String imgUrl1, String imgUrl2) {
        tvPriceFirstSupplement.setText(price1);
        tvPriceSecondSupplement.setText(price2);

        if (imgUrl1.equals("")|| imgUrl1 == null)
            Glide.with(MainDealDetailsActivity.this)
                    .load(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imFirstSupplement);
        else
            Glide.with(MainDealDetailsActivity.this)
                    .load(imgUrl1).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(imFirstSupplement);

        if (imgUrl2.equals("")|| imgUrl2 == null)
            Glide.with(MainDealDetailsActivity.this)
                    .load(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imSecondSupplement);
        else
            Glide.with(MainDealDetailsActivity.this)
                    .load(imgUrl2).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(imSecondSupplement);
    }

    @Override
    public void setFeaturesContent(String Features, String content) {
        loutFeaturesContent.setVisibility(View.VISIBLE);
        RichTextDocumentElement features = RichTextV2.textFromHtml(MainDealDetailsActivity.this, Features);
        tvFeatures.setText(features);

        RichTextDocumentElement contents = RichTextV2.textFromHtml(MainDealDetailsActivity.this, content);
        tvContent.setText(contents);

    }

    @Override
    public void onItemClickListener(String id) {

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
}
