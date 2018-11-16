package com.example.admin_linux.csdevproject.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.admin_linux.csdevproject.MainActivity;
import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.network.pojo.register_device.RDResponse;
import com.example.admin_linux.csdevproject.network.retrofit.GetDataService;
import com.example.admin_linux.csdevproject.network.retrofit.RetrofitActivityFeedInstance;
import com.example.admin_linux.csdevproject.utils.Constants;
import com.example.admin_linux.csdevproject.utils.lifecycle_callbacks.App;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends FirebaseMessagingService {

    private static final String TAG = "NS_test";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData() != null) {
            Log.d(TAG, "onMessageReceived: " + remoteMessage.getData().toString());

            if (App.isInForeground()) {
                if (remoteMessage.getData().get("ConversationType") != null) {

                    String message = remoteMessage.getData().get("body");
                    Intent intentMyIntentService = new Intent(this, MyIntentService.class);
                    intentMyIntentService.putExtra(MyIntentService.EXTRA_KEY_IN, message);
                    startService(intentMyIntentService);
                }
            } else {
                if (remoteMessage.getData().get("ConversationType") != null) {


//                    String message = remoteMessage.getData().get("body");
//                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
//                            .setSmallIcon(R.drawable.ic_dummy_default)
//                            .setContentTitle("Cropstream")
//                            .setContentText(message)
//                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//                    // notificationId is a unique int for each notification that you must define
//                    Random r = new Random();
//                    int id = r.nextInt();
//                    notificationManager.notify(id, mBuilder.build());
                }
            }
        }

//        if(remoteMessage.getNotification() != null) {
//            Log.d(TAG, "onMessageReceived: " + remoteMessage.getNotification().getBody());
//
//            String message = remoteMessage.getNotification().getBody();
//            Intent intentMyIntentService = new Intent(this, MyIntentService.class);
//            intentMyIntentService.putExtra(MyIntentService.EXTRA_KEY_IN, message);
//            startService(intentMyIntentService);
//        }
        // when app is in background -> apps receive the notification payload in the notification tray
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }


    private void scheduleJob() {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendRegistrationToServer(String token) {
        SharedPreferences preferences = getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
        String bearer = preferences.getString(Constants.PREF_PROFILE_BEARER, null);
        int personId = preferences.getInt(Constants.PREF_PROFILE_PERSON_ID, 0);
        //String deviceTokenId = preferences.getString(Constants.PREF_PROFILE_DEVICE_TOKEN, null);

        if (token != null) {

            GetDataService service = RetrofitActivityFeedInstance.getRetrofitInstance().create(GetDataService.class);
            Call<RDResponse> responseCall = service.postRegisterDevice(bearer, personId, token, Constants.DEVICE_TYPE);
            responseCall.enqueue(new Callback<RDResponse>() {
                @Override
                public void onResponse(@NonNull Call<RDResponse> call, @NonNull Response<RDResponse> response) {
                    Log.d("RegisterDevice_ns", "onResponse: " + response.body().getResultCodeName());
                }

                @Override
                public void onFailure(@NonNull Call<RDResponse> call, @NonNull Throwable t) {
                    Log.d("RegisterDevice_ns", "error: " + t.getMessage());
                }
            });
        }

        Log.d(TAG, "token: " + token);
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_dummy_default)
                        .setContentTitle(getString(R.string.notification_default_message))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}

