package com.ibtikartechs.apps.am.ui.fragments.getAddress;

import android.net.Uri;

import com.ibtikartechs.apps.am.StaticValues;
import com.ibtikartechs.apps.am.data.DataManager;
import com.ibtikartechs.apps.am.data.models.AddressModel;
import com.ibtikartechs.apps.am.ui.activities.base.BasePresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class GetAddressPresenter <V extends GetAddressMvpView> extends BasePresenter<V> implements GetAddressMvpPresenter<V> {


    public GetAddressPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getListAddresses() {

        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildUrl(getDataManager().getUser().getUserId()))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsnMainObject = new JSONObject(response.body().string());
                    if (jsnMainObject.getBoolean("status")) {
                        ArrayList<AddressModel> addressesList = new ArrayList<>();
                        JSONArray addressesArray = jsnMainObject.getJSONArray("Addressbook");
                        for (int o = 0 ; o<addressesArray.length() ; o++)
                        {
                            JSONObject jsnItemObject = addressesArray.getJSONObject(o);
                            String addressId = jsnItemObject.getString("id");
                            String name = jsnItemObject.getString("name");
                            String governmentName = jsnItemObject.getString("cityname");
                            String cityName = jsnItemObject.getString("districtname");
                            String latitude = jsnItemObject.getString("latitude");
                            String longitude = jsnItemObject.getString("longtude");
                            String address = jsnItemObject.getString("address");
                            boolean availability = jsnItemObject.getBoolean("delivary_availability");
                            addressesList.add(new AddressModel(addressId, name, governmentName, cityName, latitude, longitude, address, availability));
                        }
                        getMvpView().populateAddressesListView(addressesList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private String buildUrl(String id) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("mob")
                .appendPath("addressbook")
                .appendPath(id);
        String url = builder.build().toString();
        return url;
    }
}
