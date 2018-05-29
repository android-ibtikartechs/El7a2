package com.ibtikartechs.apps.el7a2.ui.activities.main;

import android.net.Uri;
import android.util.Log;

import com.ibtikartechs.apps.el7a2.StaticValues;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.models.UserModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by ahmedyehya on 5/1/18.
 */

public class MainPresenter <V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    private final String TAG = MainPresenter.class.getSimpleName();

    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void getCategories() {
        //getMvpView().showLoadingDialog();
        getMvpView().showProgressBar();
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildUrl())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getMvpView().showErrorView();
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String stringResponse = response.body().string();
                if (stringResponse.charAt(0) == '{') {
                    Log.d(TAG, "onResponse: " + stringResponse);

                    try {
                        JSONObject jsnmainObject = new JSONObject(stringResponse);
                        JSONArray jsnCategoryListArray = jsnmainObject.getJSONArray("List");
                        getMvpView().addMainDealFragment();
                        for (int i = 0 ; i < jsnCategoryListArray.length(); i++)
                        {
                            JSONObject jsnItemObject = jsnCategoryListArray.getJSONObject(i);
                            getMvpView().addCategoryFragment(jsnItemObject.getString("id"));
                        }

                        /*for (int i = 0 ; i < jsnCategoryListArray.length(); i++)
                        {

                            JSONObject jsnItemObject = jsnCategoryListArray.getJSONObject(i);
                            getMvpView().addCategoryTab(jsnItemObject.getString("name"),i);
                        } */

                        getMvpView().addCategoryTab("الصفقة الرئيسية",0);
                        int i = 0;
                        while (i < jsnCategoryListArray.length()){
                            JSONObject jsnItemObject = jsnCategoryListArray.getJSONObject(i);
                            getMvpView().addCategoryTab(jsnItemObject.getString("name"),++i);
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        getMvpView().showErrorView();
                    }
                    getMvpView().setupCategoryTabs();
                    getMvpView().hideErrorView();
                    //getMvpView().hideLoadingDialog();

                }
                else
                {
                    // TODO Handel Error
                    Log.d(TAG, "onResponse: " + "response is not json");
                }


            }
        });

    }

    @Override
    public Integer getNumberOfItemsInCart() {
        return getDataManager().getNumberOfItemList();
    }

    @Override
    public String getUserEmail() {
        UserModel userModel = getDataManager().getUser();
        if ( userModel != null)
            return userModel.getEmail();
        else
            return null;
    }


    private String buildUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("mobile")
                .appendPath("categories");
        String url = builder.build().toString();
        return url;
    }
}
