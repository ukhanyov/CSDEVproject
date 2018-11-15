package com.example.admin_linux.csdevproject.notifications;

import android.app.IntentService;
import android.content.Intent;



public class MyIntentService extends IntentService {

    public static final String ACTION_MyIntentService = "com.example.admin_linux.csdevproject.notifications.MyIntentService.RESPONSE";
    public static final String EXTRA_KEY_IN = "EXTRA_IN";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    String extraIn;
    String extraOut;

    public MyIntentService() {
        super("com.example.admin_linux.csdevproject.notifications.MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //get input
        extraIn = intent.getStringExtra(EXTRA_KEY_IN);
        extraOut = extraIn;

        //return result
        Intent intentResponse = new Intent();
        intentResponse.setAction(ACTION_MyIntentService);
        intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
        intentResponse.putExtra(EXTRA_KEY_OUT, extraOut);
        sendBroadcast(intentResponse);
    }

}
