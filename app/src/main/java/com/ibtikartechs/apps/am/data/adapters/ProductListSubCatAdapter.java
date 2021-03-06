package com.ibtikartechs.apps.am.data.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ibtikartechs.apps.am.MvpApp;
import com.ibtikartechs.apps.am.R;
import com.ibtikartechs.apps.am.data.DataManager;
import com.ibtikartechs.apps.am.data.models.FooterListItemModel;
import com.ibtikartechs.apps.am.ui.activities.base.BaseActivity;
import com.ibtikartechs.apps.am.ui_utilities.CustomFontTextView;
import com.ibtikartechs.apps.am.ui_utilities.CustomRecyclerView;
import com.ibtikartechs.apps.am.utilities.PaginationAdapterCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.ibtikartechs.apps.am.data.db_helper.El7a2Contract.BASE_CONTENT_URI;
import static com.ibtikartechs.apps.am.data.db_helper.El7a2Contract.PATH_CART_ITEMS;

/**
 * Created by ahmedyehya on 5/9/18.
 */

public class ProductListSubCatAdapter extends CustomBaseAdapter {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private ArrayList<FooterListItemModel> arrayList;


    private Context context;
    private customButtonListener customListener;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    Handler handler;
    Runnable runnable;
    DataManager dataManager;
    private PaginationAdapterCallback mCallback;
    private String errorMsg;

    public ProductListSubCatAdapter(Context context,
                                   ArrayList<FooterListItemModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public int getItemViewType(int position) {

        return (position == arrayList.size() -1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(FooterListItemModel r) {
        arrayList.add(r);
        notifyItemInserted(arrayList.size()-1 );
    }

    public void addAll(ArrayList<FooterListItemModel> opResults) {
        for (FooterListItemModel result : opResults) {
            add(result);
        }
    }

    public void remove(FooterListItemModel r) {
        int position = arrayList.indexOf(r);
        if (position > -1) {
            arrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }
    public FooterListItemModel getItem(int position) {
        return arrayList.get(position);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        //add(new OpportunityModel());
        add(getItem(arrayList.size()-1));
    }
    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = arrayList.size() - 1;
        FooterListItemModel result = getItem(position);

        if (result != null) {
            arrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public void onBindViewHolder(CustomRecyclerView.ViewHolder holder, final int position) {
        final FooterListItemModel model = arrayList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

                if(!(model.getImgUrl().equals("")|| model.getImgUrl()==null))
                {
                    Glide.with(context)
                            .load(model.getImgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.placeholder)
                            .into(footerViewHolder.imItem);
                }
                else {
                    Glide.with(context)
                            .load(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(footerViewHolder.imItem);
                }

                if (model.isLiked())
                    footerViewHolder.btnImLikeAction.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_liked));

                else
                    footerViewHolder.btnImLikeAction.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_unliked));


                footerViewHolder.btnImLikeAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (model.isLiked())
                            model.setLiked(false);
                        else
                            model.setLiked(true);
                        customListener.onLikeClickListener(position, model.isLiked());
                    }
                });

                dataManager.isItemExistInCartObserv(buildContentUri(model.getId())).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean)
                                    footerViewHolder.btnImAddToCart.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_cart_added));

                                else
                                    footerViewHolder.btnImAddToCart.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_cart_plus));
                            }
                        });

                if (dataManager.isItemExistInCart(buildContentUri(model.getId())))
                    footerViewHolder.btnImAddToCart.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_cart_added));

                else
                    footerViewHolder.btnImAddToCart.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_cart_plus));

                footerViewHolder.btnImAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isAdded;
                        if (!dataManager.isItemExistInCart(buildContentUri(model.getId()))) {
                            dataManager.addItemToCart(model.getId(), model.getDescription(), model.getPrice(), model.getImgUrl(), "1");
                            isAdded = true;
                        }
                        else {
                            dataManager.deleteItemFromDataBase(buildContentUri(dataManager.isItemExistinInCart(model.getId())));
                            isAdded = false;
                        }
                        customListener.onCartClickListener(position, isAdded);
                    }
                });

                footerViewHolder.tvPrice.setText(model.getPrice());
                footerViewHolder.tvTitle.setText(model.getDescription());
                if (model.isDisplayTimer()) {
                    countDownStart(model.getEndDate(), footerViewHolder);
                    footerViewHolder.tvOldPrice.setPaintFlags(footerViewHolder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    footerViewHolder.tvOldPrice.setText(model.getOldPrice());
                    footerViewHolder.tvDiscountPercent.setText(model.getDiscountPercent());
                }
                else
                    footerViewHolder.loutTimer.setVisibility(View.GONE);
                footerViewHolder.btnContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customListener.onItemClickListner(model.getId(), model.getDescription());
                    }
                });
                break;
            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) loadingVH.itemView.getLayoutParams();
                layoutParams.setFullSpan(true);


                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public Uri buildContentUri (String itemId){
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_CART_ITEMS+"/"+itemId);
        return CONTENT_URI;
    }



    @Override
    public CustomRecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        dataManager = ((MvpApp) ((BaseActivity)context).getApplication()).getDataManager();
        switch (viewType) {
            case ITEM:
                View viewItem = mInflater.inflate(
                        R.layout.view_products_list_item, viewGroup, false);
                viewHolder = new FooterViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = mInflater.inflate(R.layout.item_progress, viewGroup, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }


        return viewHolder;
    }

    public void countDownStart(final String futureTime, final FooterViewHolder footerViewHolder) {
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

                        footerViewHolder.tvDays.setText("" + String.format("%02d", days));
                        footerViewHolder.tvHours.setText("" + String.format("%02d", hours));
                        footerViewHolder.tvMinutes.setText(""
                                + String.format("%02d", minutes));
                        footerViewHolder.tvSeconds.setText(""
                                + String.format("%02d", seconds));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);


    }

    public class FooterViewHolder extends CustomRecyclerView.ViewHolder {
        @BindView(R.id.btn_lout_container)
        CardView btnContainer;
        @BindView(R.id.im_footer_item)
        ImageView imItem;
        @BindView(R.id.tv_footer_item_price)
        TextView tvPrice;
        @BindView(R.id.tv_footer_item_title)
        TextView tvTitle;
        @BindView(R.id.tv_main_deal_days)
        CustomFontTextView tvDays;
        @BindView(R.id.tv_main_deal_hours)
        CustomFontTextView tvHours;
        @BindView(R.id.tv_main_deal_minutes)
        CustomFontTextView tvMinutes;
        @BindView(R.id.tv_main_deal_seconds)
        CustomFontTextView tvSeconds;
        @BindView(R.id.loutTime)
        ConstraintLayout loutTime;
        @BindView(R.id.lout_timer_container)
        RelativeLayout loutTimer;
        @BindView(R.id.tv_main_deal_discount_parcent)
        TextView tvDiscountPercent;
        @BindView(R.id.tv_main_deal_old_price)
        TextView tvOldPrice;
        @BindView(R.id.imageView11)
        ImageView btnImLikeAction;

        @BindView(R.id.imageView10)
        ImageView btnImAddToCart;


        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.loadmore_progress)
        ProgressBar mProgressBar;
        @BindView(R.id.loadmore_retry)
        ImageButton mRetryBtn;
        @BindView(R.id.loadmore_errortxt)
        TextView mErrorTxt;
        @BindView(R.id.loadmore_errorlayout)
        LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            if (mProgressBar != null) {
                mProgressBar.setIndeterminate(true);
                mProgressBar.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.white_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(arrayList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    public interface customButtonListener {
        public void onItemClickListner(String id, String title);
        public void onLikeClickListener(int position, boolean isLiked);
        public void onCartClickListener(int position, boolean isAdded);
    }
    public void setCustomButtonListner(customButtonListener listener) {
        this.customListener = listener;
    }

    public void setPagingAdapterCallback (PaginationAdapterCallback pagingAdapterCallback)
    {
        this.mCallback = pagingAdapterCallback;
    }
}
