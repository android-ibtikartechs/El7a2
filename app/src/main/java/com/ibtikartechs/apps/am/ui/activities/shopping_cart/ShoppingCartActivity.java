package com.ibtikartechs.apps.am.ui.activities.shopping_cart;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ibtikartechs.apps.am.MvpApp;
import com.ibtikartechs.apps.am.R;
import com.ibtikartechs.apps.am.data.DataManager;
import com.ibtikartechs.apps.am.data.adapters.CartListAdapter;
import com.ibtikartechs.apps.am.data.models.CartListModel;
import com.ibtikartechs.apps.am.ui.activities.base.BaseActivity;
import com.ibtikartechs.apps.am.ui.activities.completeorder.CompleteOrderActivity;
import com.ibtikartechs.apps.am.ui.activities.registeration.RegisterationActivity;
import com.ibtikartechs.apps.am.ui_utilities.CustomFontTextView;
import com.ibtikartechs.apps.am.ui_utilities.CustomeListView;
import com.ibtikartechs.apps.am.ui_utilities.OnDetectScrollListenerListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ibtikartechs.apps.am.data.db_helper.El7a2Contract.BASE_CONTENT_URI;
import static com.ibtikartechs.apps.am.data.db_helper.El7a2Contract.PATH_CART_ITEMS;

public class ShoppingCartActivity extends BaseActivity implements ShoppingCartMvpView, CartListAdapter.CustomeListener {

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.cart_listView)
    CustomeListView cartListView;

    @BindView(R.id.lout_checkout_container)
    CardView loutCheckout;

    @BindView(R.id.tv_total_price)
    CustomFontTextView tvTotalPrice;

    @BindView(R.id.btn_lout_checkout)
    LinearLayout btnCheckout;

    String totalPrice;

    ArrayList<CartListModel> cartItemsArrayList;
    CartListAdapter cartListAdapter;

    ShoppingCartPresenter presenter;

    private Animation bottomBarAnimShow, bottomBarAnimHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        bottomBarAnimShow = AnimationUtils.loadAnimation( this, R.anim.bottom_bar_show);
        bottomBarAnimHide = AnimationUtils.loadAnimation( this, R.anim.bottom_bar_hide);
        setupActionBar();

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter.isUserLogedIn())
                    startActivity(CompleteOrderActivity.getStartIntent(ShoppingCartActivity.this));

                else {
                    Toast.makeText(ShoppingCartActivity.this, "قم بالتسجيل للتمكن من الطلب", Toast.LENGTH_SHORT).show();
                    startActivity(RegisterationActivity.getStartIntent(ShoppingCartActivity.this));
                }
            }
        });



       /* cartListView.setOnDetectScrollListener(new OnDetectScrollListenerListView() {
            @Override
            public void onUpScrolling() {
                if (loutCheckout.getVisibility()!=View.VISIBLE) {
                    loutCheckout.startAnimation(bottomBarAnimShow);
                    loutCheckout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onDownScrolling() {
                if (loutCheckout.getVisibility()!=View.GONE) {
                    loutCheckout.setVisibility(View.GONE);
                    loutCheckout.startAnimation(bottomBarAnimHide);
                }
            }

            @Override
            public void onLastItem() {
                if (loutCheckout.getVisibility()!=View.VISIBLE) {
                    loutCheckout.startAnimation(bottomBarAnimShow);
                    loutCheckout.setVisibility(View.VISIBLE);
                }
            }
        }); */

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new ShoppingCartPresenter(dataManager);
        presenter.onAttach(this);



        if (presenter.getNumberOfItemsCartList() == 0)
            btnCheckout.setEnabled(false);

        cartItemsArrayList = new ArrayList<>();
        cartItemsArrayList = presenter.getCartList();
        cartListAdapter = new CartListAdapter(this,cartItemsArrayList);
        cartListAdapter.setCustomButtonListner(this);
        cartListView.setAdapter(cartListAdapter);

        updateTotalPrice();
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

    public void setupActionBar() {
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

        ((CustomFontTextView)v.findViewById(R.id.title)).setText("سلة المشتريات");
        ((CustomFontTextView)v.findViewById(R.id.title)).setTextColor(getResources().getColor(R.color.black));

        actionBar.setCustomView(v);

    }

    @Override
    public void onRemoveButtonClickListner(String dbId, View buttonView, int position) {
        removeListItem(getViewByPosition(position, cartListView), position);
        if ((presenter.deleteItemFromCartList(buildContentUri(dbId)) == 0))
            btnCheckout.setEnabled(false);
    }

    @Override
    public void onAmountEditListener(String dpId, String amount, int position) {
        presenter.updateAmountOfItem(buildContentUri(dpId),amount);
        cartItemsArrayList.get(position).setAmount(amount);
        updateTotalPrice();
    }

    public Uri buildContentUri (String dbId){
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_CART_ITEMS+"/"+dbId);
        return CONTENT_URI;
    }

    protected void removeListItem(View rowView, final int positon) {
        final Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        rowView.startAnimation(animation);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                cartItemsArrayList.remove(positon);
                updateTotalPrice();
                cartListAdapter.notifyDataSetChanged();
                animation.cancel();


            }
        }, 100);
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public void updateTotalPrice()
    {
        Integer totalPrice = 0;
        int arrayLength = cartItemsArrayList.size();
        for (int i = 0; i<arrayLength;i++)
        {
            final CartListModel item = cartItemsArrayList.get(i);
            totalPrice+=Integer.parseInt(item.getPrice())*Integer.parseInt(item.getAmount());

        }
        this.totalPrice = String.valueOf(totalPrice);
        String resultPrice = totalPrice.toString()+" EGP";
        tvTotalPrice.setText(resultPrice);
    }
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ShoppingCartActivity.class);
        return intent;
    }
}
