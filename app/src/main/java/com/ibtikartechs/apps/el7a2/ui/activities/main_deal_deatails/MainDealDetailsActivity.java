package com.ibtikartechs.apps.el7a2.ui.activities.main_deal_deatails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainDealDetailsActivity extends AppCompatActivity {
    @BindView(R.id.im_btn_main_deal_desc_features_drop_down)
    ImageView btnFeaturesDropDown;
    @BindView(R.id.tv_main_deal_desc_features)
    CustomFontTextView tvFeatures;
    @BindView(R.id.im_btn_main_deal_desc_content_drop_down)
    ImageView btnContentDropDown;

    @BindView(R.id.tv_main_deal_desc_content)
    CustomFontTextView tvContent;
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
