package com.example.admin_linux.csdevproject;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

        Intent intent = getIntent();
        ConversationPerson person = intent.getParcelableExtra(Constants.INTENT_KEY_PERSON_TO_START_CHAT);
        mBinding.tvActivityStartChatMessage.setText(person.getMessageText());
        mBinding.tvActivityStartChatFromName.setText(person.getPersonFullName());

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
