package com.ibtikartechs.apps.el7a2.ui.activities.main_deal_deatails;

import android.net.Uri;
import android.util.Base64;

import com.ibtikartechs.apps.el7a2.StaticValues;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.models.FooterListItemModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

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
 * Created by ahmedyehya on 5/16/18.
 */

public class MainDealDetailsPresenter <V extends MainDealDetailsMvpView> extends BasePresenter<V> implements MainDealDetailsMvpPresenter<V>{

    public MainDealDetailsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getDeal(String id) {
        getMvpView().showProgressBar();
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildUrl(id, StaticValues.DEAL_PATH))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getMvpView().showErrorView();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsnMainObject = new JSONObject(response.body().string());
                    if (jsnMainObject.getString("status").equals("OK"))
                    {
                        JSONObject jsnDetailsObject = jsnMainObject.getJSONObject("details");
                        String name = jsnDetailsObject.getString("name");
                        String mainDiscription = jsnDetailsObject.getString("details");
                        byte[] data = Base64.decode(mainDiscription, Base64.DEFAULT);
                        mainDiscription = new String(data, "UTF-8");
                        String price = jsnDetailsObject.getString("price");
                        String oldPrice = jsnDetailsObject.getString("mainproductstotal");
                        String discountPercent = jsnDetailsObject.getString("discount_percentage");
                        // TODO need to change
                        boolean likeStatus = false;
                        String imgUrlBanner = "https://businessfirstfamily.com/wp-content/uploads/2017/04/sale-banners-tips-business-owners.jpg";


                        String mainImgUrl =null;
                        String firstImgUrl = null;
                        String secondImgUrl = null;
                        JSONArray jsnProductsArray = jsnDetailsObject.getJSONArray("Products");
                        JSONObject jsnProductObject;
                        for (int i = 0; i<jsnProductsArray.length(); i++)
                        {
                            jsnProductObject = jsnProductsArray.getJSONObject(i);
                            switch (i) {
                                case 0 :
                                    mainImgUrl = jsnProductObject.getString("image");
                                    break;
                                case 1 :
                                    firstImgUrl = jsnProductObject.getString("image");
                                    break;
                                case 2 :
                                    secondImgUrl = jsnProductObject.getString("image");
                            }
                        }
                        getMvpView().populateData(name, mainImgUrl, price, oldPrice,discountPercent,mainDiscription, imgUrlBanner,likeStatus);

                        getMvpView().setSupplement(firstImgUrl, secondImgUrl);

                        String endDate = jsnDetailsObject.getString("available_to");
                        String endTime = jsnDetailsObject.getString("available_to_time");

                        getMvpView().setTimer(endDate+" "+endTime);

                        String features = jsnDetailsObject.getString("specs");
                        byte[] dataF = Base64.decode(features, Base64.DEFAULT);
                        features = new String(dataF, "UTF-8");

                        String content = jsnDetailsObject.getString("features");
                        byte[] dataC = Base64.decode(content, Base64.DEFAULT);
                        content = new String(dataC, "UTF-8");

                        getMvpView().setFeaturesContent(features, content);
                        getMvpView().hideErrorView();
                        getFooter("0", StaticValues.DEAL_SUGG_CAT_PATH);
                    }
                    else
                        getMvpView().showErrorView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getProduct(String id) {
        getMvpView().showProgressBar();
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildUrl(id, StaticValues.PRODUCT_PATH))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getMvpView().showErrorView();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsnMainObject = new JSONObject(response.body().string());
                    if (jsnMainObject.getString("status").equals("OK")) {
                        JSONObject jsnDetailsObject = jsnMainObject.getJSONObject("Details");
                        String name = jsnDetailsObject.getString("name");
                        String mainImgUrl = jsnDetailsObject.getString("image");
                        String price = jsnDetailsObject.getString("price");
                        String oldPrice = jsnDetailsObject.getString("oprice");
                        String discountPercent = jsnDetailsObject.getString("discount_percentage");
                        String mainDiscription = jsnDetailsObject.getString("details");
                        byte[] data = Base64.decode(mainDiscription, Base64.DEFAULT);
                        mainDiscription = new String(data, "UTF-8");
                        // TODO need to change
                        boolean likeStatus = false;
                        String imgUrlBanner = "https://businessfirstfamily.com/wp-content/uploads/2017/04/sale-banners-tips-business-owners.jpg";
                        getMvpView().populateData(name, mainImgUrl, price, oldPrice,discountPercent,mainDiscription, imgUrlBanner,likeStatus);


                        if (!(jsnDetailsObject.getString("specs").equals("") &&  jsnDetailsObject.getString("features").equals(""))) {
                            String features = jsnDetailsObject.getString("specs");
                            byte[] dataF = Base64.decode(features, Base64.DEFAULT);
                            features = new String(dataF, "UTF-8");

                            String content = jsnDetailsObject.getString("features");
                            byte[] dataC = Base64.decode(content, Base64.DEFAULT);
                            content = new String(dataC, "UTF-8");

                            getMvpView().setFeaturesContent(features, content);



                        }
                        String endDate = jsnDetailsObject.getString("offer_end_date");
                        if (jsnDetailsObject.getString("display_timer").equals("yes"))
                        {
                            String endTime = jsnDetailsObject.getString("offer_end_time");
                            getMvpView().setTimer(endDate+" "+endTime);
                        }

                        getMvpView().hideErrorView();
                    }
                    else
                        getMvpView().showErrorView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void addItemToCart(String id, String title, String price, String imgUrl, String quantity) {
        getDataManager().addItemToCart(id, title, price, imgUrl, quantity);
    }

    @Override
    public void getFooter(String Id, String path) {
        getMvpView().hideFooterErrorView();
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildUrl(Id, path))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getMvpView().showFooterErrorView();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsnMainObject = new JSONObject(response.body().string());
                    if (jsnMainObject.getString("status").equals("OK")) {
                        JSONArray dummiesArray = jsnMainObject.getJSONArray("Details");
                        JSONObject jsnDetailsObject = dummiesArray.getJSONObject(0);
                        String catName = jsnDetailsObject.getString("name");
                        String catId = jsnDetailsObject.getString("id");
                        JSONArray jsnProductList = jsnDetailsObject.getJSONArray("Products");
                        ArrayList<FooterListItemModel> firstFooterList = new ArrayList<>();
                        for (int o = 0; o<jsnProductList.length(); o++)
                        {
                            JSONObject jsnItemObject = jsnProductList.getJSONObject(o);
                            boolean isDisplayTimer = jsnItemObject.getBoolean("display_timer");
                            firstFooterList.add(new FooterListItemModel(jsnItemObject.getString("price"), jsnItemObject.getString("image"),jsnItemObject.getString("name"),jsnItemObject.getString("offer_end_date")+" "+ jsnItemObject.getString("offer_end_time"),  jsnItemObject.getString("id"),isDisplayTimer, jsnItemObject.getString("oprice"), jsnItemObject.getString("discount_percentage")));
                        }
                        getMvpView().setFooterId(catId);
                        getMvpView().addMoreToAdapter(firstFooterList);
                        getMvpView().hideFooterErrorView();
                        getMvpView().hideFooterProgressBar();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String buildUrl(String id, String path) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("mob")
                .appendPath(path)
                .appendPath(id);
        String url = builder.build().toString();
        return url;
    }


}
