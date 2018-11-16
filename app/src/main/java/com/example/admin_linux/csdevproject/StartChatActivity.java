package com.example.admin_linux.csdevproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.admin_linux.csdevproject.data.models.ConversationPerson;
import com.example.admin_linux.csdevproject.databinding.ActivityStartChatBinding;
import com.example.admin_linux.csdevproject.utils.Constants;
import com.example.admin_linux.csdevproject.utils.CustomEditText;

import java.util.Objects;

public class StartChatActivity extends AppCompatActivity {

    ActivityStartChatBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_chat);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_start_chat);

        // Toolbar
        Toolbar toolbar = mBinding.layoutToolbar.toolbar;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(R.string.new_message);

        mBinding.tvActivityStartChatMessage.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        if(intent.getBooleanExtra(Constants.NOTIFICATION_INTENT_START_CHAT_FROM_NOTIFICATION, false)){
            String body = Objects.requireNonNull(getIntent().getExtras()).getString(Constants.NOTIFICATION_INTENT_START_CHAT_BODY);
            if(body != null && body.contains(": ")) {
                String[] split = body.split(": ");
                mBinding.tvActivityStartChatMessage.setText(split[1]);
                mBinding.tvActivityStartChatToName.setText(split[0]);
            }else {
                if(body != null && body.contains(" sent a message to you.")) {
                    mBinding.tvActivityStartChatToName.setText(body.replace(" sent a message to you.", ""));
                }
            }

        }else {
            ConversationPerson person = intent.getParcelableExtra(Constants.INTENT_KEY_PERSON_TO_START_CHAT);
            mBinding.tvActivityStartChatMessage.setText(person.getMessageText());
            mBinding.tvActivityStartChatToName.setText(person.getPersonFullName());
        }


        SharedPreferences preferences = getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
        mBinding.tvActivityStartChatFromName.setText(preferences.getString(Constants.PREF_PROFILE_FULL_NAME, null));

        //Initialise interface
        CustomEditText.OnKeyPreImeListener onKeyPreImeListener = this::finish;
        mBinding.etActivityStartChatInputText.setOnKeyPreImeListener(onKeyPreImeListener);
    }


    public void imgSendMessageOnSCClicked(View view) {
        String text = Objects.requireNonNull(mBinding.etActivityStartChatInputText.getText()).toString();
        mBinding.tvActivityStartChatYourMessage.setText(text);

        // Hide keyboard when done typing
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mBinding.etActivityStartChatInputText.getWindowToken(), 0);
        mBinding.etActivityStartChatInputText.setText(null);
    }

    public void imgPickPhotoSCClicked(View view) {
        Toast.makeText(this, "Feature is under development", Toast.LENGTH_SHORT).show();
    }


}
