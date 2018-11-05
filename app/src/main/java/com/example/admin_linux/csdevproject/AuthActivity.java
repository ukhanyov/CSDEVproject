package com.example.admin_linux.csdevproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import com.example.admin_linux.csdevproject.databinding.ActivityAuthBinding;
import com.example.admin_linux.csdevproject.utils.Constants;
import com.example.admin_linux.csdevproject.utils.URLSpanNoUnderline;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

public class AuthActivity extends AppCompatActivity{

    ActivityAuthBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_auth);

        stripUnderlines(mBinding.tvActivityAuthPrivacyPolicy);

        mBinding.tvActivityAuthPrivacyPolicy.setMovementMethod(LinkMovementMethod.getInstance());

        SharedPreferences preferences = getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
        if(preferences.getString(Constants.PREF_PROFILE_BEARER, null) != null){
            if(preferences.getBoolean(Constants.PREF_PROFILE_DEFAULT, false)){
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(Constants.KEY_INTENT_USER_FIREBASE_ID, Constants.DEFAULT_FIREBASE_USER_ID);
                intent.putExtra(Constants.KEY_INTENT_USER_FIREBASE_PHONE_NUMBER, Constants.DEFAULT_PHONE_NUMBER);
                startActivity(intent);
            }else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(Constants.KEY_INTENT_USER_FIREBASE_ID, preferences.getString(Constants.PREF_PROFILE_FIREBASE_ID, null));
                intent.putExtra(Constants.KEY_INTENT_USER_FIREBASE_PHONE_NUMBER, preferences.getString(Constants.PREF_PROFILE_PHONE_NUMBER, null));
                startActivity(intent);
            }

        }
    }

    private void stripUnderlines(TextView textView) {
        Spannable s = new SpannableString(textView.getText());
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span: spans) {
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
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.KEY_INTENT_USER_FIREBASE_ID, Constants.DEFAULT_FIREBASE_USER_ID);
        intent.putExtra(Constants.KEY_INTENT_USER_FIREBASE_PHONE_NUMBER, Constants.DEFAULT_PHONE_NUMBER);
        startActivity(intent);
    }
}
