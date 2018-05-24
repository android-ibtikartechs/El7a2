package com.ibtikartechs.apps.el7a2.ui.fragments.category;

import android.net.Uri;

import com.ibtikartechs.apps.el7a2.StaticValues;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by ahmedyehya on 5/9/18.
 */

public class CategoryPresenter <V extends CategoryMvpView> extends BasePresenter<V> implements CategoryMvpPresenter<V> {
    public CategoryPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getSubCategories(final String id) {
       /* getMvpView().showProgressBar();

        if (id.equals("34")) {
            getMvpView().addSubCategoryFragment("0");
            getMvpView().addSubCategoryFragment("1");
            getMvpView().addSubCategoryFragment("2");
            getMvpView().addSubCategoryTab("رحلات", 0);
            getMvpView().addSubCategoryTab("مواصلات", 1);
            getMvpView().addSubCategoryTab("فنادق", 2);
            getMvpView().setupSubCategoryTabs();
            }  */

        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildUrl(id))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getMvpView().showErrorView();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String stringResponse = response.body().string();
                try {
                    JSONObject jsnMainObject = new JSONObject(stringResponse);
                    getMvpView().addSubCategoryFragment(id);
                    if (jsnMainObject.getString("status").equals("OK"))
                    {
                        JSONArray jsnSubCatArray = jsnMainObject.getJSONArray("Sublist");
                        if (jsnSubCatArray != null) {
                            for (int i = 0; i < jsnSubCatArray.length(); i++) {
                                JSONObject jsnItemObject = jsnSubCatArray.getJSONObject(i);
                                getMvpView().addSubCategoryFragment(jsnItemObject.getString("id"));
                            }
                        }
                        getMvpView().addSubCategoryTab("الكل",0);

                        if (jsnSubCatArray!=null) {
                            int i = 0;
                            while (i < jsnSubCatArray.length()) {
                                JSONObject jsnItemObject = jsnSubCatArray.getJSONObject(i);
                                getMvpView().addSubCategoryTab(jsnItemObject.getString("name"), ++i);
                            }
                        }
                        getMvpView().setupSubCategoryTabs();
                        getMvpView().hideErrorView();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private String buildUrl(String catId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("mob")
                .appendPath("subcategories")
                .appendPath(catId);
        String url = builder.build().toString();
        return url;
    }
}
