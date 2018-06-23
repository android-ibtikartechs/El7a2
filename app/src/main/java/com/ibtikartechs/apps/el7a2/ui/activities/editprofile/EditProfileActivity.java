package com.ibtikartechs.apps.el7a2.ui.activities.editprofile;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BaseActivity;
import com.ibtikartechs.apps.el7a2.ui.activities.main_deal_deatails.MainDealDetailsActivity;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends BaseActivity {
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        setupActionBar("تعديل الحساب");
    }


    public void setupActionBar(String title) {
        changeLang(EditProfileActivity.this,"ar");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_act);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_action_bar_title, null);

        ((CustomFontTextView)v.findViewById(R.id.title)).setTextColor(getResources().getColor(R.color.black));
        ((CustomFontTextView)v.findViewById(R.id.title)).setText(title);

        actionBar.setCustomView(v);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
            return super.onOptionsItemSelected(item);
    }
}
