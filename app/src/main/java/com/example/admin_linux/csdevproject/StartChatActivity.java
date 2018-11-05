package com.example.admin_linux.csdevproject;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin_linux.csdevproject.data.ConversationPerson;
import com.example.admin_linux.csdevproject.databinding.ActivityStartChatBinding;
import com.example.admin_linux.csdevproject.utils.Constants;

public class StartChatActivity extends AppCompatActivity {

    ActivityStartChatBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_chat);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_start_chat);

        Intent intent = getIntent();
        ConversationPerson person = intent.getParcelableExtra(Constants.INTENT_KEY_PERSON_TO_START_CHAT);
        mBinding.tvActivityStartChat.setText(person.getPersonFullName());

    }
}
