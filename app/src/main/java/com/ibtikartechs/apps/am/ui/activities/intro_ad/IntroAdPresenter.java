package com.ibtikartechs.apps.am.ui.activities.intro_ad;

import android.net.Uri;

import com.ibtikartechs.apps.am.StaticValues;
import com.ibtikartechs.apps.am.data.DataManager;
import com.ibtikartechs.apps.am.ui.activities.base.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class IntroAdPresenter <V extends IntroAdMvpView> extends BasePresenter<V> implements IntroAdMvpPresenter<V> {

    public IntroAdPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getBannerAd(final float density) {
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(buildUrl())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getMvpView().startCountDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String stringResponse = response.body().string();
                try {
                    JSONObject jsnmainObject = new JSONObject(stringResponse);
                    String imagUrl = null;
                    if (jsnmainObject.getString("status").equals("OK"))
                    {
                        if (density>=1.0 && density<1.5)
                            imagUrl = jsnmainObject.getJSONObject("details").getString("img2");

                        else if (density>=1.5 && density<2)
                            imagUrl = jsnmainObject.getJSONObject("details").getString("img3");

                        else if (density>=2 && density<3)
                            imagUrl = jsnmainObject.getJSONObject("details").getString("img4");

                        else if (density>=3 && density<4)
                            imagUrl = jsnmainObject.getJSONObject("details").getString("img5");

                        else if (density>=4)
                            imagUrl = jsnmainObject.getJSONObject("details").getString("img5");

                        getMvpView().hideProgressBar();
                        getMvpView().setBannerView(imagUrl);
                        getMvpView().startCountDown();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private String buildUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("mob")
                .appendPath("mybanner");
        String url = builder.build().toString();
        return url;
    }

}
