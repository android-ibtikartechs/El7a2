package com.ibtikartechs.apps.am.ui.activities.registeration;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ibtikartechs.apps.am.MvpApp;
import com.ibtikartechs.apps.am.R;
import com.ibtikartechs.apps.am.data.DataManager;
import com.ibtikartechs.apps.am.data.adapters.CityAutoCompleteAdapter;
import com.ibtikartechs.apps.am.ui.activities.base.BaseActivity;
import com.ibtikartechs.apps.am.ui.activities.main.MainPresenter;
import com.ibtikartechs.apps.am.ui.activities.shopping_cart.ShoppingCartActivity;
import com.ibtikartechs.apps.am.ui_utilities.CustomAutoCompleteTextView;
import com.ibtikartechs.apps.am.ui_utilities.CustomFontEditText;
import com.ibtikartechs.apps.am.ui_utilities.CustomFontTextView;
import com.ibtikartechs.apps.am.ui_utilities.DialogFragmentActivationLink;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterationActivity extends BaseActivity implements RegisterationMvpView, CityAutoCompleteAdapter.OnAutoGovernItemClickListner , DialogFragmentActivationLink.FragmentButtonsListener {
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

@BindView(R.id.name_edtx_sign_up)
CustomFontEditText etNameSognUp;

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
@BindView(R.id.tv_btn_sign_up)
        CustomFontTextView btnSignUp;
String governId = "";
String cityId = "";
    private ProgressDialog pDialog;
    private RegisterationPresenter presenter;
    CityAutoCompleteAdapter cityAutoCompleteAdapterForAreas;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        ButterKnife.bind(this);
        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new RegisterationPresenter(dataManager);
        presenter.onAttach(this);
        handler = new Handler(Looper.getMainLooper());

        pDialog = new ProgressDialog(this,ProgressDialog.THEME_HOLO_DARK);
        pDialog.setCancelable(false);
        CityAutoCompleteAdapter cityAutoCompleteAdapter = new CityAutoCompleteAdapter(this, true);
        cityAutoCompleteAdapter.setOnAutoLocationItemClickListner(this);
        autoTexGovernrote.setAdapter(cityAutoCompleteAdapter);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etNameSognUp.getText().toString();
                String email = etEmailSignUp.getText().toString();
                String mobNum = etPhoneSignup.getText().toString();
                String address = etAddress.getText().toString();
                String cityId = RegisterationActivity.this.cityId;
                String password = etPasswordSignUp.getText().toString();
                String confPassword = etPasswordConfirmSignUp.getText().toString();
                presenter.signupRequest(name, email, mobNum, address, cityId, password, confPassword);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmailLogin.getText().toString();
                String password = etPasswordLogin.getText().toString();
                presenter.loginRequest(email, password);
            }
        });

        btnLoginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loutLogin.getVisibility()!= View.VISIBLE)
                {
                    loutSignUp.setVisibility(View.GONE);
                    loutLogin.setVisibility(View.VISIBLE);
                    btnLoginScreen.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    btnSignUpScreen.setTextColor(getResources().getColor(R.color.gray));
                }

            }
        });

        btnSignUpScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loutSignUp.getVisibility()!= View.VISIBLE)
                {
                    loutLogin.setVisibility(View.GONE);
                    loutSignUp.setVisibility(View.VISIBLE);
                    btnSignUpScreen.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    btnLoginScreen.setTextColor(getResources().getColor(R.color.gray));
                }

            }
        });

    }

    @Override
    public void onAutoLocationItemClicked(String placeId, String name, boolean isGov) {
        if (isGov) {
            autoTexGovernrote.setText(name);
            governId = placeId;
            autoTexGovernrote.dismissDropDown();
            autoTexCity.setEnabled(true);
            cityAutoCompleteAdapterForAreas = new CityAutoCompleteAdapter(this, false);
            cityAutoCompleteAdapterForAreas.setGovId(placeId);
            cityAutoCompleteAdapterForAreas.setOnAutoLocationItemClickListner(this);
            autoTexCity.setAdapter( cityAutoCompleteAdapterForAreas);
        }
        else {
            autoTexCity.setText(name);
            cityId = placeId;
            autoTexCity.dismissDropDown();
        }
        hideKeyboard();
    }

    public void hideKeyboard() {
        View view = findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showToast(final String mesg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterationActivity.this, mesg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void showProgressDialog(final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!pDialog.isShowing()) {
                    pDialog.setMessage(msg);
                    pDialog.show();
                }
            }
        });

    }

    @Override
    public void hideProgressDialog() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        });

    }


    @Override
    public void showActivationLinkDialog(String msg, String buttonTitle, final String email, final int buttonActionFlag) {
        FragmentManager fm = getSupportFragmentManager();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterationActivity.this, "email" + email, Toast.LENGTH_SHORT).show();
            }
        });

        DialogFragmentActivationLink dialogFragmentActivationLink = DialogFragmentActivationLink.newInstance(msg, buttonTitle,email,buttonActionFlag);
        dialogFragmentActivationLink.setButtonListener(this);
        dialogFragmentActivationLink.show(fm, "alert");
    }

    @Override
    public void completeAfterLogin() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
                finish();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onAlertButtonClickLisener(int buttonFlag) {

    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RegisterationActivity.class);
        return intent;
    }
}
