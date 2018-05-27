package com.ibtikartechs.apps.el7a2.ui.activities.registeration;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.adapters.CityAutoCompleteAdapter;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomAutoCompleteTextView;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontEditText;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterationActivity extends AppCompatActivity implements CityAutoCompleteAdapter.OnAutoGovernItemClickListner {
@BindView(R.id.lout_login)
LinearLayout loutLogin;

@BindView(R.id.lout_sign_up)
LinearLayout loutSignUp;

@BindView(R.id.login_txt)
CustomFontTextView btnLoginScreen;

@BindView(R.id.register_txt)
CustomFontTextView btnSignUpScreen;

@BindView(R.id.email_edtx)
CustomFontEditText etEmailLogin;

@BindView(R.id.et_password)
CustomFontEditText etPasswordLogin;

@BindView(R.id.tv_btn_login)
CustomFontTextView btnLogin;

@BindView(R.id.tv_btn_forget_password)
CustomFontTextView btnForgetPassword;

@BindView(R.id.email_edtx_sign_up)
CustomFontEditText etEmailSignUp;

@BindView(R.id.phone_edtx_sign_up)
CustomFontEditText etPhoneSignup;

@BindView(R.id.governrote_auto_tex_view_sign_up)
CustomAutoCompleteTextView autoTexGovernrote;

@BindView(R.id.city_auto_tex_view_sign_up)
CustomAutoCompleteTextView autoTexCity;

@BindView(R.id.address_edtx_sign_up)
CustomFontEditText etAddress;

@BindView(R.id.et_password_sign_up)
CustomFontEditText etPasswordSignUp;

@BindView(R.id.et_password_sign_up_confirm)
CustomFontEditText etPasswordConfirmSignUp;
String governId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        ButterKnife.bind(this);
        CityAutoCompleteAdapter cityAutoCompleteAdapter = new CityAutoCompleteAdapter(this);
        cityAutoCompleteAdapter.setOnAutoLocationItemClickListner(this);
        autoTexGovernrote.setAdapter(cityAutoCompleteAdapter);


    }

    @Override
    public void onAutoLocationItemClicked(String placeId, String name) {
        autoTexGovernrote.setText(name);
        governId = placeId;
        autoTexGovernrote.dismissDropDown();
        hideKeyboard();
        autoTexCity.setEnabled(true);
    }

    public void hideKeyboard() {
        View view = findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
