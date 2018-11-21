package com.example.admin_linux.csdevproject.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.admin_linux.csdevproject.MainActivity;
import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.databinding.ActivityAuthBinding;
import com.example.admin_linux.csdevproject.utils.Constants;
import com.example.admin_linux.csdevproject.utils.URLSpanNoUnderline;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

public class AuthActivity extends AppCompatActivity {

    ActivityAuthBinding mBinding;
    private static final String TAG = "AuthActivity_test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_auth);

        stripUnderlines(mBinding.tvActivityAuthPrivacyPolicy);

        mBinding.tvActivityAuthPrivacyPolicy.setMovementMethod(LinkMovementMethod.getInstance());

        SharedPreferences preferences = getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
        if (preferences.getString(Constants.PREF_PROFILE_BEARER, null) != null) {

            // Get token
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.d(TAG, "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                String token = Objects.requireNonNull(task.getResult()).getToken();

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(Constants.KEY_INTENT_USER_FIREBASE_ID, preferences.getString(Constants.PREF_PROFILE_FIREBASE_ID, null));
                intent.putExtra(Constants.KEY_INTENT_USER_FIREBASE_PHONE_NUMBER, preferences.getString(Constants.PREF_PROFILE_PHONE_NUMBER, null));
                intent.putExtra(Constants.KEY_INTENT_USER_FIREBASE_TOKEN, token);
                startActivity(intent);
            });
        }
    }

    private void stripUnderlines(TextView textView) {
        Spannable s = new SpannableString(textView.getText());
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span : spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            s.setSpan(span, start, end, 0);
        }
        textView.setText(s);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void btnUsePhoneNumberClicked(View view) {
        Intent intent = new Intent(this, EnterPhoneActivity.class);
        startActivity(intent);
    }

    public void btnUseTokenClicked(View view) {
        // Get token
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d(TAG, "getInstanceId failed", task.getException());
                return;
            }

            // Get new Instance ID token
            String token = Objects.requireNonNull(task.getResult()).getToken();

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(Constants.KEY_INTENT_USER_FIREBASE_ID, Constants.DEFAULT_FIREBASE_USER_ID);
            intent.putExtra(Constants.KEY_INTENT_USER_FIREBASE_PHONE_NUMBER, Constants.DEFAULT_PHONE_NUMBER);
            intent.putExtra(Constants.KEY_INTENT_USER_FIREBASE_TOKEN, token);
            startActivity(intent);
        });
    }
}
