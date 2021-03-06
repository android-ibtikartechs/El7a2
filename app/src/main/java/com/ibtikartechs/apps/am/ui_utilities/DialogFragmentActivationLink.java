package com.ibtikartechs.apps.am.ui_utilities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.ibtikartechs.apps.am.R;
import com.ibtikartechs.apps.am.StaticValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.MultipartBody.FORM;

public class DialogFragmentActivationLink extends DialogFragment {
    @BindView(R.id.customFontTextView15)
    CustomFontTextView tvTitle;
    @BindView(R.id.button2)
    Button btnAction;
    FragmentButtonsListener listener;
    private static final String ARG_MSG = "param1";
    private static final String ARG_BUTTON_TITLE = "param2";
    private static final String ARG_BUTTON_ACTION_FLAG = "param3";
    private static final String ARG_Email = "param4";
    Handler handler;


    String message, buttonTitle, email;
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
            //Toast.makeText(getActivity(), "title" + buttonTitle, Toast.LENGTH_SHORT).show();
            buttonActionFlag = getArguments().getInt(ARG_BUTTON_ACTION_FLAG);
            //Toast.makeText(getActivity(), "click" + buttonActionFlag, Toast.LENGTH_SHORT).show();
            email = getArguments().getString(ARG_Email);
            //Toast.makeText(getActivity(), "email" + email, Toast.LENGTH_SHORT).show();
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
        handler = new Handler(Looper.getMainLooper());
        super.onViewCreated(view, savedInstanceState);
        tvTitle.setText(message);
        btnAction.setText(buttonTitle);

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                //Toast.makeText(getActivity(), "click" + buttonActionFlag, Toast.LENGTH_SHORT).show();
                switch (buttonActionFlag) {
                    case 0:
                        resendActivation();
                        break;
                    case 1:
                        sendPassword();
                        Toast.makeText(getActivity(), "sendPassword", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void sendPassword() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body;
        body = new MultipartBody.Builder()
                .setType(FORM)
                .addFormDataPart("email",email).build();


        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://el7a2.ibtikarprojects.com/forgetpassword_ver2")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(getActivity(), "failer", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Toast.makeText(getActivity(), "تم إرسال كلمة المرور إلى بريدك الإلكتروني", Toast.LENGTH_SHORT).show();
                JSONObject jsnMainObject = null;
                try {
                    jsnMainObject = new JSONObject(response.body().string());
                    if (jsnMainObject.getString("status").equals("OK"))
                    {
                        /*handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "تم إرسال كلمة المرور إلى بريدك الإلكتروني", Toast.LENGTH_SHORT).show();
                            }
                        });*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

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
