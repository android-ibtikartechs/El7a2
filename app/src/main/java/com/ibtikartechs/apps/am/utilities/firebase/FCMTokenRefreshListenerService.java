package com.ibtikartechs.apps.am.utilities.firebase;

import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceIdService;

public class FCMTokenRefreshListenerService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {

        Intent intent = new Intent(this, FCMRegisteratinService.class);
        intent.putExtra("refreshed", true);
        startService(intent);
    }
}
