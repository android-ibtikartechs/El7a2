package com.ibtikartechs.apps.el7a2.ui.fragments.shippinginformation;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.adapters.ViewPagerAdapter;
import com.ibtikartechs.apps.el7a2.data.models.AddressModel;
import com.ibtikartechs.apps.el7a2.ui.fragments.add_address.AddAddressFragment;
import com.ibtikartechs.apps.el7a2.ui.fragments.getAddress.GetAddressFragment;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShippingInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShippingInformationFragment extends Fragment implements AddAddressFragment.OnNextStepListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    ViewPagerAdapter viewPagerAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    OnNextStepListener mCallBack;


    public ShippingInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShippingInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShippingInformationFragment newInstance(String param1, String param2) {
        ShippingInformationFragment fragment = new ShippingInformationFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_shipping_information, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,0,0);

        CustomFontTextView tabOne = (CustomFontTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);

        tabOne.setText("سجل العناوين");
        tabOne.setTextColor(getResources().getColor(R.color.white_blue));
        tabLayout.getTabAt(0).setCustomView(tabOne);

        CustomFontTextView tabTwo = (CustomFontTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTwo.setText("إضافة عنوان");
        tabLayout.getTabAt(1).setCustomView(tabTwo);



        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) );
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view =  ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                ((CustomFontTextView) view.findViewById(R.id.tab)).setTextColor(getResources().getColor(R.color.white_blue));


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view =  ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                ((CustomFontTextView) view.findViewById(R.id.tab)).setTextColor(getResources().getColor(R.color.gray_tab));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (OnNextStepListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onNextStepclicked(AddressModel addressModel) {
        mCallBack.onNextStepclicked(addressModel);
    }

    public interface OnNextStepListener {
        public void onNextStepclicked(AddressModel addressModel);
    }

    public void setupViewPager(ViewPager viewPager){
        viewPager.setOffscreenPageLimit(2);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        Fragment getAddressFragment = GetAddressFragment.newInstance("any","any");
        Fragment addAddressFragment = AddAddressFragment.newInstance("any","any", this);

        viewPagerAdapter.addFragment(getAddressFragment,"GetAddress fragment");
        viewPagerAdapter.addFragment(addAddressFragment,"AddAddress fragment");

    }






}
