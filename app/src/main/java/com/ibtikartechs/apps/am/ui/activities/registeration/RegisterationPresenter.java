package com.ibtikartechs.apps.am.ui.activities.registeration;

import android.util.Log;

import com.ibtikartechs.apps.am.StaticValues;
import com.ibtikartechs.apps.am.data.DataManager;
import com.ibtikartechs.apps.am.data.models.UserModel;
import com.ibtikartechs.apps.am.ui.activities.base.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.MultipartBody.FORM;


public class RegisterationPresenter <V extends RegisterationMvpView> extends BasePresenter<V> implements RegisterationMvpPresenter<V> {
    public RegisterationPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void signupRequest(String name, String email, String mobNum, String address, String cityId, String password, String confPassword) {
        if (checkValues(name,email,mobNum,address,cityId, password, confPassword)){

            getMvpView().showProgressDialog("جاري تسجيل البيانات");


            OkHttpClient client = new OkHttpClient();
            RequestBody body;
            body = new MultipartBody.Builder()
                    .setType(FORM)
                    .addFormDataPart("name", name)
                    .addFormDataPart("area",cityId)
                    .addFormDataPart("phone", mobNum)
                    .addFormDataPart("email",email)
                    .addFormDataPart("password", password)
                    .addFormDataPart("address",address).build();




            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(StaticValues.buoldUrl("register"))
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getMvpView().hideProgressDialog();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        JSONObject jsnMainObject = new JSONObject(response.body().string());
                        String email= null;
                        if (jsnMainObject.getBoolean("status"))
                        {
                            JSONObject jsnUserObject = jsnMainObject.getJSONObject("user");
                            String userId = jsnUserObject.getString("id");
                            String userName = jsnUserObject.getString("name");
                            email = jsnUserObject.getString("email");
                            String phone = jsnUserObject.getString("phone");
                            String address = jsnUserObject.getString("address");
                            String gov = jsnUserObject.getJSONArray("Addressbook").getJSONObject(0).getString("cityname");
                            String city = jsnUserObject.getJSONArray("Addressbook").getJSONObject(0).getString("districtname");
                            getDataManager().addUser(userId, userName, email, phone, address,gov, city);

                            getMvpView().hideProgressDialog();

                            UserModel userModel = getDataManager().getUser();
                            getMvpView().showToast(userModel.getEmail());
                            getMvpView().showActivationLinkDialog("لقد قمنا بإرسال ربابط التفعيل إلى البريد الإلكتروني الخاص بك قم بالتفعيل لتتمكن من الدخول إلى حسابك", "إعادة الإرسال", email, 0);
                        }
                        else
                        {
                            if (jsnMainObject.getInt("code")==0)
                                getMvpView().showActivationLinkDialog("هذا البريد الإلكتروني مسجل بالفعل ولم يتم تفعيله إضغط إرسال ليصلك رابط التفعيل على البريد الإلكتروني الخاص بك", "إرسال", email, 0);

                            else if (jsnMainObject.getInt("code")==1)
                                getMvpView().showActivationLinkDialog("هذا البريد الإلكتروني مسجل لدينا بالفعل هل نسيت كلمة المرور وتريد إستقبالها خلال بريدك الإلكتروني", "إرسال كلمة المرور",email,1);
                        }
                            getMvpView().hideProgressDialog();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }

    @Override
    public void loginRequest(String email, String password) {
        if (checkValuesForLogin(email, password))
        {
            getMvpView().showProgressDialog("جاري تسجيل الدخول");


            OkHttpClient client = new OkHttpClient();
            RequestBody body;
            body = new MultipartBody.Builder()
                    .setType(FORM)
                    .addFormDataPart("email",email)
                    .addFormDataPart("password", password).build();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(StaticValues.buoldUrl("login"))
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getMvpView().hideProgressDialog();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        JSONObject jsnMainObject = new JSONObject(response.body().string());
                        String email= null;
                        if (jsnMainObject.getBoolean("status"))
                        {
                            JSONObject jsnUserObject = jsnMainObject.getJSONObject("user");
                            String userId = jsnUserObject.getString("id");
                            String userName = jsnUserObject.getString("name");
                            email = jsnUserObject.getString("email");
                            String phone = jsnUserObject.getString("phone");
                            String address = jsnUserObject.getString("address");
                            String gov = jsnUserObject.getJSONArray("Addressbook").getJSONObject(0).getString("cityname");
                            String city = jsnUserObject.getJSONArray("Addressbook").getJSONObject(0).getString("districtname");
                            getDataManager().addUser(userId, userName, email, phone, address,gov, city);

                            getMvpView().hideProgressDialog();

                            UserModel userModel = getDataManager().getUser();
                            getMvpView().showToast(userModel.getEmail());
                            getMvpView().completeAfterLogin();
                        }
                        else
                        {
                            if (jsnMainObject.getInt("code")==0)
                                getMvpView().showActivationLinkDialog("هذا البريد الإلكتروني مسجل بالفعل ولم يتم تفعيله إضغط إرسال ليصلك رابط التفعيل على البريد الإلكتروني الخاص بك وبعد التفعيل قم بالدخول مرة أخرى", "إعادة الإرسال", email, 0);

                            else if (jsnMainObject.getInt("code")==2)
                                getMvpView().showActivationLinkDialog("كلمة المرور غير صحيحة هل نسيت كلمة المرور وتريد إستقبالها خلال بريدك الإلكتروني", "إرسال كلمة المرور",email,1);

                            else if (jsnMainObject.getInt("code")==3)
                            {
                                getMvpView().showToast("تأكد من بياناتك ثم حاول مرة أخرى");
                            }
                        }
                        getMvpView().hideProgressDialog();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }

    boolean checkValuesForLogin (String email, String Password)
    {
        if (email.isEmpty() || Password.isEmpty())
        {
            getMvpView().showToast("برجاء ملأ الحقول الفارغة");
            return false;
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            getMvpView().showToast("برجاء إدخال الإيميل بصيغة سليمة");
            return false;
        }
        else
            return true;

    }

    boolean checkValues(String name, String email, String mobNum, String address, String cityId, String password, String confPassword)
    {
        if (name.isEmpty() || email.isEmpty() || mobNum.isEmpty() || address.isEmpty() || password.isEmpty()) {
            getMvpView().showToast("برجاء ملأ الحقول الفارغة");
            return false;
        }
        else if (cityId.isEmpty()) {
            getMvpView().showToast("برجاء إختيار المحافظة والمدينة");
            return false;
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            getMvpView().showToast("برجاء إدخال الإيميل بصيغة سليمة");
            return false;
        }
        else if (!password.equals(confPassword))
        {
            getMvpView().showToast("كلمة المرور غير متطابقة");
            return false;
        }
        else
            return true;
    }
}
