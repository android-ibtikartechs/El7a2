package com.ibtikartechs.apps.am.ui.fragments.subcategory;

import android.net.Uri;

import com.ibtikartechs.apps.am.StaticValues;
import com.ibtikartechs.apps.am.data.DataManager;
import com.ibtikartechs.apps.am.data.models.FooterListItemModel;
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

/**
 * Created by ahmedyehya on 5/9/18.
 */

public class SubCategoryPresenter <V extends SubCategoryMvpView> extends BasePresenter<V> implements SubCategoryMvpPresenter<V> {
    public SubCategoryPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadFirstPage(String subCategoryId) {

        getMvpView().showProgressBar();
        getMvpView().setLastPageTrue();

            OkHttpClient client = new OkHttpClient();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(buildUrl(subCategoryId))
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
                        if (jsnMainObject.getString("status").equals("OK"))
                        {
                            JSONArray jsnProductArray= jsnMainObject.getJSONArray("Products");
                            ArrayList<FooterListItemModel> productList = new ArrayList<>();
                            for (int i =0; i<jsnProductArray.length(); i++)
                            {
                                JSONObject jsnProductObject = jsnProductArray.getJSONObject(i);
                                productList.add(new FooterListItemModel(jsnProductObject.getString("price"), jsnProductObject.getString("image"),jsnProductObject.getString("name"),jsnProductObject.getString("offer_end_date")+" "+ jsnProductObject.getString("offer_end_time"),  jsnProductObject.getString("id"),jsnProductObject.getBoolean("display_timer"), jsnProductObject.getString("oprice"), jsnProductObject.getString("discount_percentage")));
                            }
                            getMvpView().addMoreToAdapter(productList);
                            getMvpView().hideErrorView();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

    }

    private String buildUrl(String subCatId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("mob")
                .appendPath("products")
                .appendPath(subCatId);
        String url = builder.build().toString();
        return url;
    }

    @Override
    public void loadNextPage(String id, Integer page) {

    }
}
