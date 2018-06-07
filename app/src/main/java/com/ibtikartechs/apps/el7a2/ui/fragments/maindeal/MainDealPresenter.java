package com.ibtikartechs.apps.el7a2.ui.fragments.maindeal;

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
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.MultipartBody.FORM;

/**
 * Created by ahmedyehya on 5/3/18.
 */

public class MainDealPresenter <V extends MainDealMvpView> extends BasePresenter<V> implements MainDealMvpPresenter<V> {

    private final String TAG = MainDealPresenter.class.getSimpleName();

    public MainDealPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void getMainDealData() {
        getMvpView().showProgressBar();
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildUrl())
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
                    JSONObject jsnmainObject = new JSONObject(stringResponse);
                    JSONObject jsnDeatailsObject = jsnmainObject.getJSONObject("details");
                    String dealName = jsnDeatailsObject.getString("name");
                    String id = jsnDeatailsObject.getString("id");
                    String mainProductImgUrl = jsnDeatailsObject.getJSONArray("Products").getJSONObject(0).getString("image");
                    String dealPrice = jsnDeatailsObject.getString("price");
                    String oldPrice = jsnDeatailsObject.getString("mainproductstotal");
                    String discountPercent = jsnDeatailsObject.getString("discount_percentage");
                    int numOfFooters = jsnDeatailsObject.getInt("no_frontcats");
                    String endDate = jsnDeatailsObject.getString("available_to");
                    String endTime = jsnDeatailsObject.getString("available_to_time");
                    String firstproductImgUrl = jsnDeatailsObject.getJSONArray("Products").getJSONObject(1).getString("image");
                    String secondProductImgUrl = jsnDeatailsObject.getJSONArray("Products").getJSONObject(2).getString("image");
                    JSONArray jsnBannersArry = jsnDeatailsObject.getJSONArray("Banners");
                    String firstBannerImgUrl = jsnBannersArry.getJSONObject(0).getString("banner");
                    String secondBannerImgUrl = jsnBannersArry.getJSONObject(1).getString("banner");
                    String firstBannerId = jsnBannersArry.getJSONObject(0).getString("product");
                    String secondBannerId = jsnBannersArry.getJSONObject(1).getString("product");
                    String dealDetails = jsnDeatailsObject.getString("details");
                    byte[] data = Base64.decode(dealDetails, Base64.DEFAULT);
                    dealDetails = new String(data, "UTF-8");
                    getMvpView().hideErrorView();
                    getMvpView().setNumofFooters(numOfFooters);
                    getMvpView().populateData(mainProductImgUrl,dealName,dealPrice,endDate +" "+endTime,firstproductImgUrl,secondProductImgUrl,dealDetails,oldPrice,discountPercent, numOfFooters, firstBannerImgUrl, secondBannerImgUrl, firstBannerId, secondBannerId);
                    getMvpView().setDealId(id);


                } catch (JSONException e) {
                    e.printStackTrace();
                    getMvpView().showErrorView();
                }
            }
        });
    }

    @Override
    public void getFooterList() {
        getMvpView().hideFooterProgressBar();
        ArrayList<FooterListItemModel> firstFooterList = new ArrayList<>();
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-06-01 05:00:00"));
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-05-21 17:00:00"));
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-07-03 05:00:00"));
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-10-01 05:00:00"));
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-6-10 05:00:00"));
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-07-01 05:00:00"));
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-07-01 05:00:00"));
        getMvpView().addMoreToAdapter(firstFooterList);
    }

    @Override
    public void supscribe(String email) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body;
        body = new MultipartBody.Builder()
                .setType(FORM)
                .addFormDataPart("email", email).build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(StaticValues.buoldUrl("savenewsletter"))
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getMvpView().showToast("خطأ في الاتصال حاول مرة أخرى");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsnMainObject = new JSONObject(response.body().string());
                    if (jsnMainObject.getString("status").equals("OK"))
                    {
                        getMvpView().showToast("أنت الآن تتابع النشرة البريدية لإلحق");
                    }
                    else if (jsnMainObject.getString("status").equals("false"))
                    {
                        if (jsnMainObject.getString("msg").equals("already exists"))
                            getMvpView().showToast("أنت بالفعل تتابع نشرتنا البريدية");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getFooter(final int numOfFooter, final ArrayList<Integer> currentFootersId) {
        if (numOfFooter == 1 && currentFootersId.size()<numOfFooter)
        {
            OkHttpClient client = new OkHttpClient();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(footerBuildUrl())
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

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
                            getMvpView().showFooter2(catName,firstFooterList, catId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        else if (numOfFooter != 1 && currentFootersId.size()<numOfFooter)
        {

            OkHttpClient client = new OkHttpClient();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(footerBuildUrl())
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

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
                            if (numOfFooter >= 3) {
                                if (currentFootersId.size() == 0) {
                                    getMvpView().showFooter1(catName, firstFooterList, catId);
                                }
                                if (currentFootersId.size()==1)
                                {
                                    getMvpView().showFooter2(catName,firstFooterList,catId);
                                }
                                if (currentFootersId.size() ==2)
                                    getMvpView().showFooter3(catName, firstFooterList, catId);
                            }
                            else if (numOfFooter == 2) {
                                if (currentFootersId.size() == 0) {
                                    getMvpView().showFooter1(catName, firstFooterList, catId);
                                }
                                if (currentFootersId.size()==1)
                                {
                                    getMvpView().showFooter2(catName,firstFooterList,catId);
                                }

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }



    }

    private String buildUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("mob")
                .appendPath("maindeal");
        String url = builder.build().toString();
        return url;
    }

    private String footerBuildUrl ()
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("mob")
                .appendPath("frontpage_categories");
        String url = builder.build().toString();
        return url;
    }


}
