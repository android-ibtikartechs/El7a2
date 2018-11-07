package com.ibtikartechs.apps.am.ui.fragments.subcategory;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ibtikartechs.apps.am.MvpApp;
import com.ibtikartechs.apps.am.R;
import com.ibtikartechs.apps.am.StaticValues;
import com.ibtikartechs.apps.am.data.DataManager;
import com.ibtikartechs.apps.am.data.adapters.AdressListAdapter;
import com.ibtikartechs.apps.am.data.adapters.ProductListSubCatAdapter;
import com.ibtikartechs.apps.am.data.adapters.ProductListSubCatAdapter$FooterViewHolder_ViewBinding;
import com.ibtikartechs.apps.am.data.models.FooterListItemModel;
import com.ibtikartechs.apps.am.ui.activities.base.BaseFragment;
import com.ibtikartechs.apps.am.ui.activities.main_deal_deatails.MainDealDetailsActivity;
import com.ibtikartechs.apps.am.ui_utilities.CustomFontTextView;
import com.ibtikartechs.apps.am.ui_utilities.CustomRecyclerView;
import com.ibtikartechs.apps.am.utilities.PaginationAdapterCallback;
import com.ibtikartechs.apps.am.utilities.paginationStaggardScrollListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubCategoryFragment extends BaseFragment implements SubCategoryMvpView, ProductListSubCatAdapter.customButtonListener, PaginationAdapterCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String subcategoryId;
    private String mParam2;
    @BindView(R.id.lout_sub_category_data_layout)
    ConstraintLayout loutData;
    @BindView(R.id.rv_sub_category_list_products)
    CustomRecyclerView rvListProducts;
    @BindView(R.id.main_progress)
    ProgressBar mainProgressBar;
    @BindView(R.id.error_btn_retry)
    Button btnRetry;
    @BindView(R.id.tv_main_deal_error_txt_cause)
    CustomFontTextView teexErrorCause;
    @BindView(R.id.lout_main_deal_error_layout)
    LinearLayout loutError;
    @BindView(R.id.error_layout)
    CardView loutMainError;
    customButtonListener customListener;


    ProductListSubCatAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    private static final int PAGE_START = 1;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 20;
    private Integer currentPage = PAGE_START;

    private ArrayList<FooterListItemModel> arrayList;



    Handler handler;
    SubCategoryPresenter presenter;




    public SubCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubCategoryFragment newInstance(String param1, String param2, customButtonListener customListener) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        fragment.setCustomButtonListner(customListener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subcategoryId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        handler = new Handler(Looper.getMainLooper());
        DataManager dataManager = ((MvpApp) getActivity().getApplication()).getDataManager();
        presenter = new SubCategoryPresenter(dataManager);
        presenter.onAttach(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sub_category, container, false);
        ButterKnife.bind(this, rootView);
        if (mainProgressBar != null) {
            mainProgressBar.setIndeterminate(true);
            mainProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
            mainProgressBar.setVisibility(View.VISIBLE);
        }
        arrayList = new ArrayList<>();
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadFirstPage(subcategoryId);
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        rvListProducts.setLayoutManager(staggeredGridLayoutManager);
        rvListProducts.setHasFixedSize(true);

        rvListProducts.setItemViewCacheSize(20);
        rvListProducts.setDrawingCacheEnabled(true);
        rvListProducts.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        populatRecyclerView();
        implementScrolListener();

        currentPage = PAGE_START;
        presenter.loadFirstPage(subcategoryId);



        super.onViewCreated(view, savedInstanceState);
    }

    private void populatRecyclerView() {

        adapter = new ProductListSubCatAdapter(getActivity(), arrayList);
        adapter.setCustomButtonListner(this);
        adapter.setPagingAdapterCallback(this);
        rvListProducts.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void hideErrorView() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                loutMainError.setVisibility(View.GONE);
                loutData.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void showErrorView() {
        handler.post(new Runnable() {
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
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void showProgressBar() {
        loutError.setVisibility(View.GONE);
        mainProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setLastPageTrue() {
        isLastPage = true;
    }

    @Override
    public void addMoreToAdapter(final ArrayList<FooterListItemModel> list) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.addAll(list);
            }
        });
    }

    @Override
    public void addLoadingFooter() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.addLoadingFooter();
            }
        });
    }

    @Override
    public void removeLoadingFooter() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.removeLoadingFooter();
            }
        });
    }

    @Override
    public void showRetryAdapter() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.showRetry(true,getString(R.string.conn_error_recyview));
            }
        });
    }

    @Override
    public void setIsLoadingFalse() {
        isLoading = false;
    }

    @Override
    public void retryPageLoad() {
        presenter.loadNextPage(subcategoryId, currentPage);
    }

    @Override
    public void onItemClickListner(String id, String title) {
        startActivity(MainDealDetailsActivity.getStartIntent(getActivity(),id, StaticValues.PROD_FLAG, title));
    }

    @Override
    public void onLikeClickListener(int position, boolean isLiked) {
        if (isLiked)
            ((ImageView)((View)((ProductListSubCatAdapter.FooterViewHolder) rvListProducts.findViewHolderForAdapterPosition(position)).itemView).findViewById(R.id.imageView11)).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_action_liked));

        else
            ((ImageView)((View)((ProductListSubCatAdapter.FooterViewHolder) rvListProducts.findViewHolderForAdapterPosition(position)).itemView).findViewById(R.id.imageView11)).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_action_unliked));
    }

    @Override
    public void onCartClickListener(int position, boolean isAdded) {
        if (isAdded)
            ((ImageView)((View)((ProductListSubCatAdapter.FooterViewHolder) rvListProducts.findViewHolderForAdapterPosition(position)).itemView).findViewById(R.id.imageView10)).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_action_cart_added));

        else
            ((ImageView)((View)((ProductListSubCatAdapter.FooterViewHolder) rvListProducts.findViewHolderForAdapterPosition(position)).itemView).findViewById(R.id.imageView10)).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_action_cart_plus));

        customListener.onItemCartChange();
    }

    private void implementScrolListener()
    {
        rvListProducts.addOnScrollListener(new paginationStaggardScrollListener(staggeredGridLayoutManager) {
            @Override
            protected void hideCatList() {

            }

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                presenter.loadNextPage(subcategoryId, currentPage);
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
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    public interface customButtonListener {
        public void onItemCartChange();
    }
    public void setCustomButtonListner(customButtonListener listener) {
        this.customListener = listener;
    }
}
