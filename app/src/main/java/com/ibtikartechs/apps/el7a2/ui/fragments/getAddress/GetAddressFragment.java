package com.ibtikartechs.apps.el7a2.ui.fragments.getAddress;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ibtikartechs.apps.el7a2.MvpApp;
import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.adapters.AdressListAdapter;
import com.ibtikartechs.apps.el7a2.data.models.AddressModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BaseFragment;
import com.ibtikartechs.apps.el7a2.ui.activities.shopping_cart.ShoppingCartPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GetAddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetAddressFragment extends BaseFragment implements GetAddressMvpView, AdressListAdapter.CustomeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.lv_addresses)
    ListView lvAddresses;

    @BindView(R.id.linearLayout5)
    LinearLayout btnNextStep;

    private Handler mHandler;
    GetAddressPresenter presenter;
    AdressListAdapter adressListAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int oldPosition;
    OnNextStepListener onNextStepListener;
    private AddressModel selectedAddress;


    public GetAddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GetAddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GetAddressFragment newInstance(String param1, String param2, OnNextStepListener onNextStepListener) {
        GetAddressFragment fragment = new GetAddressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        fragment.onNextStepListener = onNextStepListener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mHandler = new Handler(Looper.getMainLooper());

        DataManager dataManager = ((MvpApp) getActivity().getApplication()).getDataManager();
        presenter = new GetAddressPresenter(dataManager);
        presenter.onAttach(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_get_address, container, false);
        ButterKnife.bind(this, rootView);

        presenter.getListAddresses();
        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedAddress == null)
                    Toast.makeText(getActivity(), "برجاء اختيار عنوان الشحن للانتقال للخطوة التالية", Toast.LENGTH_SHORT).show();
                else
                    onNextStepListener.onNextStepclickedAfterGetAddress(selectedAddress);
            }
        });
        return rootView;
    }

    @Override
    public void onSelectedItem(AddressModel addressModel, int position) {
        Toast.makeText(getActivity(), addressModel.getAddress(), Toast.LENGTH_SHORT).show();
        ((ImageView)getViewByPosition(oldPosition, lvAddresses).findViewById(R.id.ic_check_indicator)).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick_unselected));
        ((ImageView)getViewByPosition(position, lvAddresses).findViewById(R.id.ic_check_indicator)).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick_selected));
        selectedAddress = addressModel;
        oldPosition = position;
    }

    @Override
    public void populateAddressesListView(final ArrayList<AddressModel> addressesArrayList)
    {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                adressListAdapter = new AdressListAdapter(getActivity(),addressesArrayList);
                adressListAdapter.setCustomButtonListner(GetAddressFragment.this);
                lvAddresses.setAdapter(adressListAdapter);
            }
        });

    }




    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public interface OnNextStepListener {
        public void onNextStepclickedAfterGetAddress(AddressModel addressModel);
    }

    public void setOnNextStepListener(OnNextStepListener onNextStepListener)
    {
        this.onNextStepListener = onNextStepListener;
    }

}
