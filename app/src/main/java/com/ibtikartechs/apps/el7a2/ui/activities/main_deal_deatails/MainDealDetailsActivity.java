package com.ibtikartechs.apps.el7a2.ui.activities.main_deal_deatails;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainDealDetailsActivity extends AppCompatActivity {
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
    CustomFontTextView tvMainDescription;
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
    CustomFontTextView tvFeatures;
    @BindView(R.id.im_btn_main_deal_desc_content_drop_down)
    ImageView btnContentDropDown;

    @BindView(R.id.tv_main_deal_desc_content)
    CustomFontTextView tvContent;

    @BindView(R.id.tv_main_deal_days)
    CustomFontTextView tvDays;
    @BindView(R.id.tv_main_deal_hours)
    CustomFontTextView tvHours;
    @BindView(R.id.tv_main_deal_minutes)
    CustomFontTextView tvMinutes;
    @BindView(R.id.tv_main_deal_seconds)
    CustomFontTextView tvSeconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_deal_details);
        ButterKnife.bind(this);
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
    }
}
