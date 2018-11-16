package com.example.admin_linux.csdevproject.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.admin_linux.csdevproject.ConversationDetailsActivity;
import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.StartChatActivity;
import com.example.admin_linux.csdevproject.network.pojo.register_device.RDResponse;
import com.example.admin_linux.csdevproject.network.retrofit.GetDataService;
import com.example.admin_linux.csdevproject.network.retrofit.RetrofitActivityFeedInstance;
import com.example.admin_linux.csdevproject.utils.Constants;
import com.example.admin_linux.csdevproject.utils.lifecycle_callbacks.App;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends FirebaseMessagingService {

    private static final String TAG = "NS_test_fcm";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData() != null) {
            Log.d(TAG, "onMessageReceived: " + remoteMessage.getData().toString());

            if (App.isInForeground()) {

                if (remoteMessage.getData().get("notificationType").equals("ConversationMessage")) {

                    String message = remoteMessage.getData().get("body");
                    Intent intentMyIntentService = new Intent(this, MyIntentService.class);
                    intentMyIntentService.putExtra(MyIntentService.EXTRA_KEY_IN, message);
                    startService(intentMyIntentService);
                }

                if (remoteMessage.getData().get("notificationType").equals("CardTemplatedMessagePosted")) {
                    String message = remoteMessage.getData().get("body");
                    Intent intentMyIntentService = new Intent(this, MyIntentService.class);
                    intentMyIntentService.putExtra(MyIntentService.EXTRA_KEY_IN, message);
                    startService(intentMyIntentService);
                }
            } else {


                if (remoteMessage.getData().get("notificationType").equals("ConversationMessage"))
                    sendNotificationToConversation(remoteMessage.getData().get("body"),
                            remoteMessage.getData().get("title"),
                            Integer.valueOf(Objects.requireNonNull(remoteMessage.getData().get("PersonId"))),
                            Integer.valueOf(Objects.requireNonNull(remoteMessage.getData().get("ConversationId"))));

                if (remoteMessage.getData().get("notificationType").equals("CardTemplatedMessagePosted")) {
                    sendNotificationToStartChat(remoteMessage.getData().get("CardRenderDataId"),
                            remoteMessage.getData().get("body"),
                            remoteMessage.getData().get("title"),
                            remoteMessage.getData().get("FeedEventId"));
                }

            }
        }

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


//    private void scheduleJob() {
//        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
//        Job myJob = dispatcher.newJobBuilder()
//                .setService(MyJobService.class)
//                .setTag("my-job-tag")
//                .build();
//        dispatcher.schedule(myJob);
//    }
//
//    private void handleNow() {
//        Log.d(TAG, "Short lived task is done.");
//    }

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
                    if (response.body() != null)
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

    private void sendNotificationToConversation(String messageBody, String title, int personId, int ConversationId) {
        Intent intent = new Intent(this, ConversationDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.NOTIFICATION_INTENT_CONVERSATION_FROM_NOTIFICATION, true);
        intent.putExtra(Constants.NOTIFICATION_INTENT_CONVERSATION_TITLE, title);
        intent.putExtra(Constants.NOTIFICATION_INTENT_CONVERSATION_PERSON_ID, personId);
        intent.putExtra(Constants.NOTIFICATION_INTENT_CONVERSATION_CONVERSATION_ID, ConversationId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.ic_dummy_default)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.channel_id),
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(channel);
        }

        Random random = new Random();
        notificationManager.notify(random.nextInt(), notificationBuilder.build());
    }

    private void sendNotificationToStartChat(String cardRenderDataId, String body, String title, String feedEventId) {
        Intent intent = new Intent(this, StartChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.NOTIFICATION_INTENT_START_CHAT_FROM_NOTIFICATION, true);
        intent.putExtra(Constants.NOTIFICATION_INTENT_START_CHAT_CARD_RENDER_ID, cardRenderDataId);
        intent.putExtra(Constants.NOTIFICATION_INTENT_START_CHAT_BODY, body);
        intent.putExtra(Constants.NOTIFICATION_INTENT_START_CHAT_TITLE, title);
        intent.putExtra(Constants.NOTIFICATION_INTENT_START_CHAT_FEED_EVENT_ID, feedEventId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.ic_dummy_default)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.channel_id),
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(channel);
        }

        Random random = new Random();
        notificationManager.notify(random.nextInt(), notificationBuilder.build());
    }
}

