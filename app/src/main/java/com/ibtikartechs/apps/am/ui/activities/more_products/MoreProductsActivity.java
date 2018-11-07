package com.ibtikartechs.apps.am.ui.activities.more_products;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ibtikartechs.apps.am.MvpApp;
import com.ibtikartechs.apps.am.R;
import com.ibtikartechs.apps.am.StaticValues;
import com.ibtikartechs.apps.am.data.DataManager;
import com.ibtikartechs.apps.am.data.adapters.ProductListSubCatAdapter;
import com.ibtikartechs.apps.am.data.models.FooterListItemModel;
import com.ibtikartechs.apps.am.ui.activities.base.BaseActivity;
import com.ibtikartechs.apps.am.ui.activities.main_deal_deatails.MainDealDetailsActivity;
import com.ibtikartechs.apps.am.ui.activities.main_deal_deatails.MainDealDetailsPresenter;
import com.ibtikartechs.apps.am.ui.activities.shopping_cart.ShoppingCartActivity;
import com.ibtikartechs.apps.am.ui.fragments.searchdialogfragment.SearchDialogFragment;
import com.ibtikartechs.apps.am.ui_utilities.CustomFontTextView;
import com.ibtikartechs.apps.am.ui_utilities.CustomRecyclerView;
import com.ibtikartechs.apps.am.utilities.PaginationAdapterCallback;
import com.ibtikartechs.apps.am.utilities.paginationStaggardScrollListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreProductsActivity extends BaseActivity implements MoreProductsMvpView, ProductListSubCatAdapter.customButtonListener, PaginationAdapterCallback {
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;

    @BindView(R.id.rv_more_list_products)
    CustomRecyclerView rvProducts;

    @BindView(R.id.error_layout)
    CardView loutMainError;
    @BindView(R.id.main_progress)
    ProgressBar mainProgressBar;
    @BindView(R.id.tv_main_deal_error_txt_cause)
    CustomFontTextView teexErrorCause;
    @BindView(R.id.error_btn_retry)
    Button btnRetry;
    @BindView(R.id.lout_main_deal_error_layout)
    LinearLayout loutError;

    Handler mHandler;
    TextView textCartItemCount;
    private MoreProductsPresenter presenter;
    String catId;
    private static final int PAGE_START = 1;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 20;
    private Integer currentPage = PAGE_START;

    private ArrayList<FooterListItemModel> arrayList;


    ProductListSubCatAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_products);
        mHandler = new Handler(Looper.getMainLooper());
        ButterKnife.bind(this);

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new MoreProductsPresenter(dataManager);
        presenter.onAttach(this);

        Intent intent = getIntent();
        catId = intent.getStringExtra(StaticValues.KEY_ID);
        String catName = intent.getStringExtra(StaticValues.KEY_PRODUCT_NAME);
        setupActionBar(catName);
        if (mainProgressBar != null) {
            mainProgressBar.setIndeterminate(true);
            mainProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
            mainProgressBar.setVisibility(View.VISIBLE);
        }

        arrayList = new ArrayList<>();

        setupRecyclerView();

        presenter.getProductsListFirstPage(catId);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getProductsListFirstPage(catId);
            }
        });
    }

    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    private void setupRecyclerView() {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        rvProducts.setLayoutManager(staggeredGridLayoutManager);
        rvProducts.setHasFixedSize(true);

        rvProducts.setItemViewCacheSize(20);
        rvProducts.setDrawingCacheEnabled(true);
        rvProducts.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        adapter = new ProductListSubCatAdapter(this, arrayList);
        adapter.setCustomButtonListner(this);
        adapter.setPagingAdapterCallback(this);
        rvProducts.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        implementScrolListener();
    }

    private void implementScrolListener()
    {
        rvProducts.addOnScrollListener(new paginationStaggardScrollListener(staggeredGridLayoutManager) {
            @Override
            protected void hideCatList() {

            }

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                presenter.getNextPage(catId, currentPage);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.others_menu,menu);
        final MenuItem cartItem = menu.findItem(R.id.action_cart);

        View actionView = MenuItemCompat.getActionView(cartItem);
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
        setupBadge();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(cartItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_search:
                FragmentManager fm = getSupportFragmentManager();
                SearchDialogFragment searchDialogFragment = new SearchDialogFragment();
                searchDialogFragment.show(fm, "alert");
                return true;

            case R.id.action_cart:
                startActivity(ShoppingCartActivity.getStartIntent(this));
        }
        return super.onOptionsItemSelected(item);
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

    public static Intent getStartIntent(Context context, String catlId, String catName) {
        Intent intent = new Intent(context, MoreProductsActivity.class);
        intent.putExtra(StaticValues.KEY_ID, catlId);
        intent.putExtra(StaticValues.KEY_PRODUCT_NAME, catName);
        return intent;
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            textCartItemCount.setText(String.valueOf(Math.min(presenter.getNumberOfItemsInCart(), 99)));
        }
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
    public void addMoreToAdapter(final ArrayList<FooterListItemModel> list) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                adapter.addAll(list);
            }
        });
    }

    @Override
    public void onItemClickListner(String id, String title) {
        startActivity(MainDealDetailsActivity.getStartIntent(this,id, StaticValues.PROD_FLAG, title));
    }

    @Override
    public void onLikeClickListener(int position, boolean isLiked) {
        if (isLiked)
            ((ImageView)((View)((ProductListSubCatAdapter.FooterViewHolder) rvProducts.findViewHolderForAdapterPosition(position)).itemView).findViewById(R.id.imageView11)).setImageDrawable(getResources().getDrawable(R.drawable.ic_action_liked));

        else
            ((ImageView)((View)((ProductListSubCatAdapter.FooterViewHolder) rvProducts.findViewHolderForAdapterPosition(position)).itemView).findViewById(R.id.imageView11)).setImageDrawable(getResources().getDrawable(R.drawable.ic_action_unliked));
    }

    @Override
    public void onCartClickListener(int position, boolean isAdded) {
        if (isAdded)
            ((ImageView)((View)((ProductListSubCatAdapter.FooterViewHolder) rvProducts.findViewHolderForAdapterPosition(position)).itemView).findViewById(R.id.imageView10)).setImageDrawable(getResources().getDrawable(R.drawable.ic_action_cart_added));

        else
            ((ImageView)((View)((ProductListSubCatAdapter.FooterViewHolder) rvProducts.findViewHolderForAdapterPosition(position)).itemView).findViewById(R.id.imageView10)).setImageDrawable(getResources().getDrawable(R.drawable.ic_action_cart_plus));

        setupBadge();
    }

    @Override
    public void retryPageLoad() {
        presenter.getNextPage(catId, currentPage);
    }
}
