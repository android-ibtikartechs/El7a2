package com.ibtikartechs.apps.el7a2;

import android.net.Uri;

/**
 * Created by ahmedyehya on 5/2/18.
 */

public class StaticValues {
    public static final String URL_AUOTHORITY = "el7a2.ibtikarprojects.com";
    public static String KEY_ID = "keyId";


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
