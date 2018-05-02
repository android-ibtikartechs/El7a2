package com.ibtikartechs.apps.el7a2.ui.fragments;


import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainDealFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainDealFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Handler handler;
    private Runnable runnable;
    private int year;
    private int month;
    private int day;
    static Date currentDate;
    @BindView(R.id.tv_main_deal_days) CustomFontTextView tvDays;
    @BindView(R.id.tv_main_deal_hours) CustomFontTextView tvHours;
    @BindView(R.id.tv_main_deal_minutes) CustomFontTextView tvMinutes;
    @BindView(R.id.tv_main_deal_seconds) CustomFontTextView tvSeconds;
    @BindView(R.id.tv_main_deal_old_price) TextView tvOldPrice;

    public MainDealFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainDealFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainDealFragment newInstance(String param1, String param2) {
        MainDealFragment fragment = new MainDealFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_deal, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvOldPrice.setPaintFlags(tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        countDownStart("2018-05-03");
    }

    public void countDownStart(final String futureTime) {
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

                        tvDays.setText("" + String.format("%02d", days));
                        tvHours.setText("" + String.format("%02d", hours));
                        tvMinutes.setText(""
                                + String.format("%02d", minutes));
                        tvSeconds.setText(""
                                + String.format("%02d", seconds));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);


    }

}
