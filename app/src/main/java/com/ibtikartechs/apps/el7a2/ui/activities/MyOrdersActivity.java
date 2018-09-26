package com.ibtikartechs.apps.el7a2.ui.activities;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.hosamazzam.volleysimple.VolleySimple;
import com.ibtikartechs.apps.el7a2.MvpApp;
import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.StaticValues;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.adapters.Orders_Adapter;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BaseActivity;
import com.ibtikartechs.apps.el7a2.ui.activities.main_deal_deatails.MainDealDetailsActivity;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;
import com.ibtikartechs.apps.el7a2.utilities.OrderResponse;

public class MyOrdersActivity extends BaseActivity {

    Orders_Adapter orders_adapter;
    RecyclerView list;
    VolleySimple volleySimple;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        list = findViewById(R.id.order_list);
        toolbar = findViewById(R.id.main_toolbar);
        volleySimple = VolleySimple.getInstance(this);
        list.setLayoutManager(new LinearLayoutManager(this));
        init(dataManager.getUser().getUserId());
        setupActionBar("طلباتي");
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

    public void init(String id) {
        ProgressDialog progressDialog = ProgressDialog.show(MyOrdersActivity.this, "انتظر من فضلك", "", false, false);
        volleySimple.asyncStringGet("http://"+ StaticValues.URL_AUOTHORITY + "/mob/"+"mybookings/" + id, new VolleySimple.NetworkListener<String>() {
            @Override
            public void onResponse(String s) {
                OrderResponse response = new Gson().fromJson(s, OrderResponse.class);
                if (response.getStatus().equals("OK")) {
                    orders_adapter = new Orders_Adapter(MyOrdersActivity.this, response.getOrder());
                    list.setAdapter(orders_adapter);
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        }, progressDialog);
    }


    public void setupActionBar(String title) {
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

}
