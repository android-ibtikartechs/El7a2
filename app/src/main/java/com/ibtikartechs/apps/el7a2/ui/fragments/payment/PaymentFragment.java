package com.ibtikartechs.apps.el7a2.ui.fragments.payment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.models.AddressModel;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    @BindView(R.id.tv_name)
    CustomFontTextView tvName;
    @BindView(R.id.tv_address)
    CustomFontTextView tvAddress;
    @BindView(R.id.tv_govern_city)
    CustomFontTextView tvGov_City;
    @BindView(R.id.tv_availability)
    CustomFontTextView tvAvailability;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    public void pushData(AddressModel addressModel)
    {
        tvName.setText(addressModel.getName());
        tvAddress.setText(addressModel.getAddress());
        String city = addressModel.getCityName()+" - " + addressModel.getGovernmentName();
        tvGov_City.setText(city);
        if (addressModel.isDelivaryAvailabl())
        {
            tvAvailability.setTextColor(getActivity().getResources().getColor(R.color.green));
            tvAvailability.setText("متاح");
        }
        else
        {
            tvAvailability.setTextColor(getActivity().getResources().getColor(R.color.ColorRedHint));
            tvAvailability.setText("غير متاح");
        }
    }

}
