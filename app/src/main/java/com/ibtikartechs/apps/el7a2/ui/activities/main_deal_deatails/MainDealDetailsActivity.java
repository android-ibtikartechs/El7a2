package com.ibtikartechs.apps.el7a2.ui.activities.main_deal_deatails;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ibtikartechs.apps.el7a2.MvpApp;
import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.StaticValues;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.adapters.FooterListAdapter;
import com.ibtikartechs.apps.el7a2.data.models.FooterListItemModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BaseActivity;
import com.ibtikartechs.apps.el7a2.ui.activities.shopping_cart.ShoppingCartActivity;
import com.ibtikartechs.apps.el7a2.ui.fragments.maindeal.MainDealPresenter;
import com.ibtikartechs.apps.el7a2.ui.fragments.searchdialogfragment.SearchDialogFragment;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;
import com.ibtikartechs.apps.el7a2.ui_utilities.GlideTarget;

import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.square1.richtextlib.spans.RemoteBitmapSpan;
import io.square1.richtextlib.spans.UrlBitmapDownloader;
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
    CardView loutSupplements;
    @BindView(R.id.im_main_deal_first_sale)
    ImageView imFirstSupplement;
    @BindView(R.id.im_main_deal_second_sale)
    ImageView imSecondSupplement;
    @BindView(R.id.customFontTextView12)
    WebView tvMainDescription;

    @BindView(R.id.tv_count_down)
    CustomFontTextView tvCountDown;
    @BindView(R.id.card_footer)
    CardView footerCard;
    @BindView(R.id.imageView6)
    ImageView imBanner;

    @BindView(R.id.lout_feature)
    ConstraintLayout loutFeature;




    @BindView(R.id.imageView4)
    ImageView imSomeFooter;


    @BindView(R.id.tv_main_deal_desc_suggested_category_name)
    CustomFontTextView btntvSuggCategoryName;

    @BindView(R.id.tv_btn_main_deal_desc_suggested_category_more)
    CustomFontTextView btnMoreSuggCategory;

    @BindView(R.id.rv_main_deal_suggested_category_products)
    RecyclerView rvSuggCategoryProducts;



    @BindView(R.id.im_btn_main_deal_desc_features_drop_down)
    ImageView btnFeaturesDropDown;
    @BindView(R.id.tv_main_deal_desc_features)
    WebView tvFeatures;
    @BindView(R.id.im_btn_main_deal_desc_content_drop_down)
    ImageView btnContentDropDown;

    @BindView(R.id.tv_main_deal_desc_content)
    WebView tvContent;


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

    @BindView(R.id.tv_btn_add_to_cart)
    CustomFontTextView btnBuy;

    @BindView(R.id.spin_add_to_cart)
    Spinner spinQuantity;

    private Handler mHandler;
    private Handler handler;
    MainDealDetailsPresenter presenter;
    private FooterListAdapter firstListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<FooterListItemModel> firstArrayList;
    private Runnable runnable;
    private String dealOrProductId;
    private int dealOrProduct;
    String selectedQuantity;
    String titleOfProduct;
    String priceOfProduct;
    String imgUrlOfProduct;
    private String footerCatId;
    TextView textCartItemCount;
    @BindView(R.id.lout_buy_footer)
    CardView loutBuyFooter;

    @BindView(R.id.lout_content)
    ConstraintLayout loutContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_deal_details);
        mHandler = new Handler(Looper.getMainLooper());
        ButterKnife.bind(this);
        tvOldPrice.setPaintFlags(tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Intent intent = getIntent();
        dealOrProductId = intent.getStringExtra(StaticValues.KEY_ID);
        dealOrProduct = intent.getIntExtra(StaticValues.KEY_FLAG_PRODUCT_OR_DEAL, 101);
        if(dealOrProduct == StaticValues.DEAL_FLAG)
            setupActionBar("الصفقة الرئيسية");
        else {
            titleOfProduct = intent.getStringExtra(StaticValues.KEY_PRODUCT_NAME);
            setupActionBar(titleOfProduct);
        }

        Log.d("intent", "onCreate: " + "deal or product id = " + dealOrProductId);
        if (mainProgressBar != null) {
            mainProgressBar.setIndeterminate(true);
            mainProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
            mainProgressBar.setVisibility(View.VISIBLE);
        }



        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new MainDealDetailsPresenter(dataManager);
        presenter.onAttach(this);

       /* if (presenter.checkIfExistInShoppingCart(dealOrProductId)!=null) {
            btnBuy.setText("تعديل الكمية");
            loutBuyFooter.setCardBackgroundColor(ContextCompat.getColor(this,R.color.red));
            spinQuantity.setSelection(Integer.valueOf(presenter.checkIfExistInShoppingCart(dealOrProductId).getAmount())-1);
            selectedQuantity = (String) spinQuantity.getSelectedItem();
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.updateAmountOfItem(presenter.checkIfExistInShoppingCart(dealOrProductId).getDpId(), (String) spinQuantity.getSelectedItem());
                }
            });
        }

        else {
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.addItemToCart(dealOrProductId, titleOfProduct, priceOfProduct, imgUrlOfProduct, selectedQuantity);
                    setupBadge();
                    btnBuy.setText("تعديل الكمية");
                    loutBuyFooter.setCardBackgroundColor(ContextCompat.getColor(MainDealDetailsActivity.this,R.color.red));
                    spinQuantity.setSelection(Integer.valueOf(presenter.checkIfExistInShoppingCart(dealOrProductId).getAmount())-1);
                    selectedQuantity = (String) spinQuantity.getSelectedItem();
                    btnBuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            presenter.updateAmountOfItem(presenter.checkIfExistInShoppingCart(dealOrProductId).getDpId(), (String) spinQuantity.getSelectedItem());
                        }
                    });
                }
            });
        }*/

        ArrayAdapter<String> amountSpinnerAdapter;
        String[] amountsArray = {"1","2","3","4","5","6","7","8","9","10"};

        amountSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.view_spinner_row_footer_buy, R.id.language, amountsArray);
        amountSpinnerAdapter.setDropDownViewResource(R.layout.view_spinner_amount_dropdown_row);

        spinQuantity.setAdapter(amountSpinnerAdapter);

        spinQuantity.setSelection(0);

        spinQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);

                if (!TextUtils.isEmpty(selection)) {
                    selectedQuantity = selection;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });


        firstArrayList = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        rvSuggCategoryProducts.setLayoutManager(linearLayoutManager);
        rvSuggCategoryProducts.setHasFixedSize(true);

        rvSuggCategoryProducts.setItemViewCacheSize(20);
        rvSuggCategoryProducts.setDrawingCacheEnabled(true);
        rvSuggCategoryProducts.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        populatRecyclerView();

     /*   btnFeaturesDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvFeatures.getVisibility() == View.GONE)
                    tvFeatures.setVisibility(View.VISIBLE);
                else
                    tvFeatures.setVisibility(View.GONE);
            }
        });*/

        loutFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvFeatures.getVisibility() == View.GONE)
                    tvFeatures.setVisibility(View.VISIBLE);
                else
                    tvFeatures.setVisibility(View.GONE);
            }
        });

        loutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvContent.getVisibility() == View.GONE)
                    tvContent.setVisibility(View.VISIBLE);
                else
                    tvContent.setVisibility(View.GONE);
            }
        });

      /*  btnContentDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvContent.getVisibility() == View.GONE)
                    tvContent.setVisibility(View.VISIBLE);
                else
                    tvContent.setVisibility(View.GONE);
            }
        });
        */

        if (dealOrProduct == StaticValues.DEAL_FLAG)
        {
            presenter.getDeal(dealOrProductId);
        }
        else
            presenter.getProduct(dealOrProductId);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dealOrProduct == StaticValues.DEAL_FLAG)
                {
                    presenter.getDeal(dealOrProductId);
                }
                else
                    presenter.getProduct(dealOrProductId);
            }
        });
    }

    @Override
    protected void onResume() {
        setupBadge();
        if (presenter.checkIfExistInShoppingCart(dealOrProductId)!=null) {
            btnBuy.setText("تعديل الكمية");
            loutBuyFooter.setCardBackgroundColor(ContextCompat.getColor(this,R.color.red));
            spinQuantity.setSelection(Integer.valueOf(presenter.checkIfExistInShoppingCart(dealOrProductId).getAmount())-1);
            selectedQuantity = (String) spinQuantity.getSelectedItem();
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.updateAmountOfItem(presenter.checkIfExistInShoppingCart(dealOrProductId).getDpId(), (String) spinQuantity.getSelectedItem());
                }
            });
        }

        else {
            btnBuy.setText("أضف لمشترياتي");
            loutBuyFooter.setCardBackgroundColor(ContextCompat.getColor(this,R.color.white_blue));
            spinQuantity.setSelection(0);
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.addItemToCart(dealOrProductId, titleOfProduct, priceOfProduct, imgUrlOfProduct, selectedQuantity);
                    setupBadge();
                    btnBuy.setText("تعديل الكمية");
                    loutBuyFooter.setCardBackgroundColor(ContextCompat.getColor(MainDealDetailsActivity.this,R.color.red));
                    spinQuantity.setSelection(Integer.valueOf(presenter.checkIfExistInShoppingCart(dealOrProductId).getAmount())-1);
                    selectedQuantity = (String) spinQuantity.getSelectedItem();
                    btnBuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            presenter.updateAmountOfItem(presenter.checkIfExistInShoppingCart(dealOrProductId).getDpId(), (String) spinQuantity.getSelectedItem());
                        }
                    });
                }
            });
        }
        super.onResume();

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

    private void setupBadge() {

        if (textCartItemCount != null) {
            textCartItemCount.setText(String.valueOf(Math.min(presenter.getNumberOfItemsInCart(), 99)));
        }
    }

    private void populatRecyclerView() {
        firstListAdapter = new FooterListAdapter(this, firstArrayList);
        firstListAdapter.setCustomButtonListner(this);
        rvSuggCategoryProducts.setAdapter(firstListAdapter);
        firstListAdapter.notifyDataSetChanged();
    }

    public void setupActionBar(String title) {
        changeLang(MainDealDetailsActivity.this,"ar");
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
                             final String discountPercent, final String mainDescription, final String imgUrlBanner, boolean likeStatus) {

        titleOfProduct = dealName;
        priceOfProduct = price;
        imgUrlOfProduct = mainImageUrl;


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


                String texPrice = price + " EGP";
                tvMainPrice.setText(texPrice);

                String texOldPrice = oldPrice + " EGP";
                tvOldPrice.setText(texOldPrice);
                tvDiscountPercent.setText(discountPercent);

                tvMainDescription.setWebViewClient(new WebViewClient() {

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        // TODO Auto-generated method stub

                        return false;
                    }
                });
                WebSettings webSettings2 = tvMainDescription.getSettings();
                webSettings2.setJavaScriptEnabled(true);
                tvMainDescription.loadDataWithBaseURL("", buildHtml(mainDescription), "text/html", "utf-8", "");




              /*  RichTextDocumentElement description = RichTextV2.textFromHtml(MainDealDetailsActivity.this, mainDescription);
                tvMainDescription.setText(description); */

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
    }

    @Override
    public void showFooterProgressBar() {

    }

    @Override
    public void showFooterErrorView() {

    }

    @Override
    public void hideFooterErrorView() {


    }

    @Override
    public void setTimer(final String time) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                tvCountDown.setVisibility(View.VISIBLE);
                countDownStart(time);
            }
        });

    }

    @Override
    public void setSupplement(final String imgUrl1, final String imgUrl2) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                loutSupplements.setVisibility(View.VISIBLE);

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
        });

    }

    @Override
    public void setFeaturesContent(final String Features, final String content) {


        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String frameVideo = "<html><body>Video From YouTube<br><iframe width=\"420\" height=\"315\" src=\"https://www.youtube.com/embed/XUTXU6fy94E\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
                WebSettings webSettings = tvFeatures.getSettings();
                webSettings.setJavaScriptEnabled(true);

                tvFeatures.setWebViewClient(new WebViewClient() {

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        // TODO Auto-generated method stub
                        view.loadUrl(url);
                        return true;
                    }
                });


                tvFeatures.loadDataWithBaseURL("", buildHtml(Features), "text/html", "utf-8", "");

/*
                tvFeatures.setUrlBitmapDownloader(new UrlBitmapDownloader() {

                    @Override
                    public void downloadImage(RemoteBitmapSpan urlBitmapSpan, Uri image) {
                        Glide.with(MainDealDetailsActivity.this)
                                .load(image)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(new GlideTarget(MainDealDetailsActivity.this,urlBitmapSpan));
                    }

                }); */
                loutFeaturesContent.setVisibility(View.VISIBLE);
            /*    RichTextDocumentElement features = RichTextV2.textFromHtml(MainDealDetailsActivity.this, Features);
                tvFeatures.setText(features); */

              /*  RichTextDocumentElement contents = RichTextV2.textFromHtml(MainDealDetailsActivity.this, content);
                tvContent.setText(contents); */

                tvContent.setWebViewClient(new WebViewClient() {

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        // TODO Auto-generated method stub
                        view.loadUrl(url);
                        return true;
                    }
                });
                WebSettings webSettings2 = tvContent.getSettings();
                webSettings2.setJavaScriptEnabled(true);
                tvContent.loadDataWithBaseURL("", buildHtml(content), "text/html", "utf-8", "");


            }
        });


    }

    @Override
    public void setFooterId(String footerCatId , final String footerCatName) {
        this.footerCatId = footerCatId;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                btntvSuggCategoryName.setText(footerCatName);
            }
        });



    }

    @Override
    public void showFooter() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                footerCard.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onItemClickListener(String id, String title) {

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

                        tvCountDown.setText(String.format("%02d", days)+" " + getString(R.string.day_sample) + " : "
                                +String.format("%02d", hours)+" " + getString(R.string.hours_sample)  + " : "
                                +String.format("%02d", minutes)+" " + getString(R.string.monutes_sample) + " : "
                                +String.format("%02d", seconds)+" " + getString(R.string.seconds_sample));

                    /*    tvDays.setText("" + String.format("%02d", days));
                        tvHours.setText("" + String.format("%02d", hours));
                        tvMinutes.setText(""
                                + String.format("%02d", minutes));
                        tvSeconds.setText(""
                                + String.format("%02d", seconds)); */

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);


    }
    public static Intent getStartIntent(Context context, String dealId, int dealOrProduct, String productName) {
        Intent intent = new Intent(context, MainDealDetailsActivity.class);
        intent.putExtra(StaticValues.KEY_ID, dealId);
        intent.putExtra(StaticValues.KEY_FLAG_PRODUCT_OR_DEAL, dealOrProduct);
        intent.putExtra(StaticValues.KEY_PRODUCT_NAME, productName);
        return intent;
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
