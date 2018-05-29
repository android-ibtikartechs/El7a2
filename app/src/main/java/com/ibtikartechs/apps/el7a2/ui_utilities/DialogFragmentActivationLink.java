package com.ibtikartechs.apps.el7a2.ui_utilities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.StaticValues;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogFragmentActivationLink extends DialogFragment {
    @BindView(R.id.customFontTextView15)
    CustomFontTextView tvTitle;
    @BindView(R.id.button2)
    Button btnAction;
    FragmentButtonsListener listener;
    private static final String ARG_MSG = "param1";
    private static final String ARG_BUTTON_TITLE = "param2";
    private static final String ARG_BUTTON_ACTION_FLAG = "param3";
    private static final String ARG_Email = "param3";

    String message, buttonTitle;
    int buttonActionFlag;

    public static DialogFragmentActivationLink newInstance(String msg, String buttonTitle, String email, int buttonActionFlag) {
        DialogFragmentActivationLink fragment = new DialogFragmentActivationLink();
        Bundle args = new Bundle();
        args.putString(ARG_MSG, msg);
        args.putString(ARG_BUTTON_TITLE, buttonTitle);
        args.putInt(ARG_BUTTON_ACTION_FLAG, buttonActionFlag);
        args.putString(ARG_Email, email);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString(ARG_MSG);
            buttonTitle = getArguments().getString(ARG_BUTTON_TITLE);
            buttonActionFlag = getArguments().getInt(ARG_BUTTON_ACTION_FLAG);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_resend_activation_link, container, false);
        ButterKnife.bind(this,rootView);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle.setText(message);
        btnAction.setText(buttonTitle);

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                switch (buttonActionFlag) {
                    case 0:
                        resendActivation();
                        break;
                    case 1:
                        sendPassword();
                }
            }
        });
    }

    private void sendPassword() {
    }

    private void resendActivation() {

    }

    public interface FragmentButtonsListener
    {
        public void onAlertButtonClickLisener (int buttonFlag);
    }
    public void setButtonListener (FragmentButtonsListener listener)
    {
        this.listener = listener;
    }
}
