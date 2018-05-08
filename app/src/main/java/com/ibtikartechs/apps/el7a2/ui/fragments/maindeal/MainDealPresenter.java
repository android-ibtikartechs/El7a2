package com.ibtikartechs.apps.el7a2.ui.fragments.maindeal;

import android.net.Uri;
import android.util.Base64;

import com.ibtikartechs.apps.el7a2.StaticValues;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.models.FooterListItemModel;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

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
                    String mainProductImgUrl = jsnDeatailsObject.getJSONArray("Products").getJSONObject(0).getString("image");
                    String dealPrice = jsnDeatailsObject.getString("price");
                    String endDate = jsnDeatailsObject.getString("available_to");
                    String firstproductImgUrl = jsnDeatailsObject.getJSONArray("Products").getJSONObject(1).getString("image");
                    String secondProductImgUrl = jsnDeatailsObject.getJSONArray("Products").getJSONObject(2).getString("image");

                    String dealDetails = jsnDeatailsObject.getString("details");
                    byte[] data = Base64.decode(dealDetails, Base64.DEFAULT);
                    dealDetails = new String(data, "UTF-8");
                    getMvpView().hideErrorView();
                    getMvpView().populateData(mainProductImgUrl,dealName,dealPrice,endDate,firstproductImgUrl,secondProductImgUrl,dealDetails);
                    getFooterList();
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
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-05-08"));
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-06-01"));
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-05-18"));
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-07-03"));
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-10-01"));
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-5-10"));
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-07-01"));
        firstFooterList.add(new FooterListItemModel("0","2200$","https://i5.walmartimages.com/asr/f5608778-2a32-4b49-bbdc-10b7edb11e19_1.f583d9ee5b2fc78a0dd64a305adf3ef4.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF","ايفون جميل جدا ايفون جميل جدا ايفون جميل جدا","2018-07-01"));
        getMvpView().addMoreToAdapter(firstFooterList);
    }

    private String buildUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("mobile")
                .appendPath("maindeal");
        String url = builder.build().toString();
        return url;
    }
}
