package com.example.admin_linux.csdevproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.admin_linux.csdevproject.data.models.ConversationPerson;
import com.example.admin_linux.csdevproject.databinding.ActivityStartChatBinding;
import com.example.admin_linux.csdevproject.utils.Constants;

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
        ConversationPerson person = intent.getParcelableExtra(Constants.INTENT_KEY_PERSON_TO_START_CHAT);
        mBinding.tvActivityStartChatMessage.setText(person.getMessageText());
        mBinding.tvActivityStartChatFromName.setText(person.getPersonFullName());

        SharedPreferences preferences = getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
        mBinding.tvActivityStartChatToName.setText(preferences.getString(Constants.PREF_PROFILE_FULL_NAME, null));

    }

    public void imgSendMessageOnSCClicked(View view) {
        String text = mBinding.etActivityStartChatInputText.getText().toString();
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
