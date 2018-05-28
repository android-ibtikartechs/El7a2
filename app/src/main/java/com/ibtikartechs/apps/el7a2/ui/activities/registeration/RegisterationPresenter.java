package com.ibtikartechs.apps.el7a2.ui.activities.registeration;

import com.ibtikartechs.apps.el7a2.StaticValues;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.models.UserModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;


public class RegisterationPresenter <V extends RegisterationMvpView> extends BasePresenter<V> implements RegisterationMvpPresenter<V> {
    public RegisterationPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void signupRequest(String name, String email, String mobNum, String address, String cityId, String password, String confPassword) {
        if (checkValues(name,email,mobNum,address,cityId, password, confPassword)){
            getMvpView().showProgressDialog("جاري تسجيل البيانات");
            OkHttpClient client = new OkHttpClient();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(StaticValues.buoldUrl("register"))
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getMvpView().hideProgressDialog();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String respons = response.body().string();
                        JSONObject jsnMainObject = new JSONObject(response.body().string());
                        if (jsnMainObject.getString("status").equals("OK"))
                        {
                            JSONObject jsnUserObject = jsnMainObject.getJSONObject("user");
                            String userId = jsnUserObject.getString("id");
                            String userName = jsnUserObject.getString("name");
                            String email = jsnUserObject.getString("email");
                            String phone = jsnUserObject.getString("phone");
                            String address = jsnUserObject.getString("address");
                            String gov = jsnUserObject.getJSONArray("Addressbook").getJSONObject(0).getString("cityname");
                            String city = jsnUserObject.getJSONArray("Addressbook").getJSONObject(0).getString("districtname");
                            getDataManager().addUser(userId, userName, email, phone, address,gov, city);

                            getMvpView().hideProgressDialog();
                        }
                        else
                            getMvpView().hideProgressDialog();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        UserModel userModel = getDataManager().getUser();
        getMvpView().showToast(userModel.getEmail());
    }

    @Override
    public void loginRequest(String email, String password) {

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
