package com.ibtikartechs.apps.el7a2.ui.fragments.add_address;

import com.ibtikartechs.apps.el7a2.StaticValues;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.models.AddressModel;
import com.ibtikartechs.apps.el7a2.data.models.UserModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

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

public class AddAddressPresenter <V extends AddAddressMvpView> extends BasePresenter<V> implements AddAddressMvpPresenter<V> {

    public AddAddressPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void addAddress(String name, String cityId, String postalCode, final String latitude, String longitude, final String mobNum, String address) {
        if (checkValues(name, cityId, postalCode, latitude, longitude, mobNum, address))
        {
            getMvpView().showProgressDialog("جاري إضافة عنوانك إلى سجل العناوين");
            OkHttpClient client = new OkHttpClient();
            RequestBody body;
            body = new MultipartBody.Builder()
                    .setType(FORM)
                    .addFormDataPart("user",getUserData().getUserId())
                    .addFormDataPart("district", cityId)
                    .addFormDataPart("postalcode", postalCode)
                    .addFormDataPart("latitude", latitude)
                    .addFormDataPart("longtude", longitude)
                    .addFormDataPart("name", name)
                    .addFormDataPart("mobile", mobNum)
                    .addFormDataPart("address", address)
                    .build();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(StaticValues.buoldUrl("addaddressbook"))
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getMvpView().showToast("حدث خطا في الإتصال");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        JSONObject jsnMainObject = new JSONObject(response.body().string());
                        String addressId = jsnMainObject.getString("id");
                        String name = jsnMainObject.getString("name");
                        String mobile= jsnMainObject.getString("mobile");
                        String countryName = jsnMainObject.getString("countryname");
                        String governName = jsnMainObject.getString("cityname");
                        String cityName = jsnMainObject.getString("districtname");
                        String lat = jsnMainObject.getString("latitude");
                        String longit = jsnMainObject.getString("longtude");
                        String address = jsnMainObject.getString("address");
                        String fees = jsnMainObject.getString("fees");
                        boolean delivaryAvailability = jsnMainObject.getBoolean("delivary_availability");

                        AddressModel addressModel = new AddressModel(addressId,countryName,governName,cityName,latitude,longit,name,mobNum,address,delivaryAvailability,fees);
                        getMvpView().hideProgressDialog();
                        getMvpView().showToast("تم إضافة هذا العنوان إلى سجل عناوينك");
                        getMvpView().passAddress(addressModel);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    @Override
    public UserModel getUserData() {
        return getDataManager().getUser();
    }

    private boolean checkValues(String name, String cityId, String postalCode, String latitude, String longitude, String mobNum, String address)
    {
        if (name.isEmpty() || postalCode.isEmpty() || mobNum.isEmpty()) {
            getMvpView().showToast("برجاء ملأ الحقول الفارغة");
            return false;
        }
        else if (cityId.isEmpty())
        {
            getMvpView().showToast("برجاء اختيار المحافظة والمدينة");
            return false;
        }
        else if (latitude.isEmpty() || longitude.isEmpty())
        {
            getMvpView().showToast("برجاء تحديد عنوانك على الخريطة");
            getMvpView().refreshLocationPermision();
            return false;
        }

        else if (address.isEmpty())
        {
            getMvpView().showToast("برجاء عدم ترك العنوان أسفل الخريطة فارغا");
            return false;
        }

        else
            return true;

    }
}
