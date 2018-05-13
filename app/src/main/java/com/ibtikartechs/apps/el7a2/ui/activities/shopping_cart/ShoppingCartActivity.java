package com.ibtikartechs.apps.el7a2.ui.activities.shopping_cart;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.ibtikartechs.apps.el7a2.MvpApp;
import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.adapters.CartListAdapter;
import com.ibtikartechs.apps.el7a2.data.models.CartListModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BaseActivity;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ibtikartechs.apps.el7a2.data.db_helper.El7a2Contract.BASE_CONTENT_URI;
import static com.ibtikartechs.apps.el7a2.data.db_helper.El7a2Contract.PATH_CART_ITEMS;

public class ShoppingCartActivity extends BaseActivity implements ShoppingCartMvpView, CartListAdapter.CustomeListener {

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.cart_listView)
    ListView cartListView;

    ArrayList<CartListModel> cartItemsArrayList;
    CartListAdapter cartListAdapter;

    ShoppingCartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);

        setupActionBar();

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new ShoppingCartPresenter(dataManager);
        presenter.onAttach(this);

        presenter.addItemToCart("0", "سيارة كيا الجديدة كليا", "20000.00$", "http://www.sellanycar.com/cars-related/ar/wp-content/uploads/2014/11/2015-Kia-Sportage-new-design-620x340.jpg");

        cartItemsArrayList = new ArrayList<>();
        cartItemsArrayList = presenter.getCartList();
        cartListAdapter = new CartListAdapter(this,cartItemsArrayList);
        cartListAdapter.setCustomButtonListner(this);
        cartListView.setAdapter(cartListAdapter);

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

        ((CustomFontTextView)v.findViewById(R.id.title)).setText("سلة المشتريات");

        actionBar.setCustomView(v);

    }

    @Override
    public void onRemoveButtonClickListner(String dbId, View buttonView, int position) {
        removeListItem(getViewByPosition(position, cartListView), position);
        presenter.deleteItemFromCartList(buildContentUri(dbId));
    }

    @Override
    public void onAmountEditListener(String dpId, String amount) {
        presenter.updateAmountOfItem(buildContentUri(dpId),amount);
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
}
