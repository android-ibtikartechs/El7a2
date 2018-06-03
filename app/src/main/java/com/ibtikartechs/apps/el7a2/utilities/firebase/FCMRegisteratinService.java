package com.ibtikartechs.apps.el7a2.utilities.firebase;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.iid.FirebaseInstanceId;

public class FCMRegisteratinService extends IntentService {
    SharedPreferences preferences;
    public FCMRegisteratinService() {
        super("FCM");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String token = FirebaseInstanceId.getInstance().getToken();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (intent.getExtras() != null) {
            boolean refreshed = intent.getExtras().getBoolean("refreshed");
            if (refreshed) preferences.edit().putBoolean("token_sent", false).apply();
        }

        // if token_sent value is false then use method sendTokenToServer to send token to server
        if (!preferences.getBoolean("token_sent", false))
            sendTokenToServer(token);

    }

    private void sendTokenToServer(String token) {



    }


}
