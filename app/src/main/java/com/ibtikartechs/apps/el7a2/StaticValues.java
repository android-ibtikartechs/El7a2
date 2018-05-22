package com.ibtikartechs.apps.el7a2;

import android.net.Uri;

/**
 * Created by ahmedyehya on 5/2/18.
 */

public class StaticValues {
    public static final String URL_AUOTHORITY = "el7a2.ibtikarprojects.com";
    public static final String KEY_ID = "keyId";
    public static final String KEY_FLAG_PRODUCT_OR_DEAL = "key_prod_or_deal";
    public static final int DEAL_FLAG = 100;
    public static final int PROD_FLAG = 101;

    public static String buoldUrl(String endpoint) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("mobile")
                .appendPath(endpoint);
        String url = builder.build().toString();
        return url;
    }
}
