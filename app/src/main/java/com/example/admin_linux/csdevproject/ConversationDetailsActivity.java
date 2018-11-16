package com.example.admin_linux.csdevproject;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.admin_linux.csdevproject.adapters.CDMessageAdapter;
import com.example.admin_linux.csdevproject.adapters.CDPeopleAdapter;
import com.example.admin_linux.csdevproject.data.ConversationDetailsViewModel;
import com.example.admin_linux.csdevproject.data.models.ConversationDetailsMessage;
import com.example.admin_linux.csdevproject.databinding.ActivityConversationDetailsBinding;
import com.example.admin_linux.csdevproject.network.pojo.conversation_details.ConversationDetailsReturnValue;
import com.example.admin_linux.csdevproject.network.pojo.conversation_details.model.CDConversationModel;
import com.example.admin_linux.csdevproject.network.pojo.conversation_details.model.participants.CDParticipants;
import com.example.admin_linux.csdevproject.network.retrofit.GetDataService;
import com.example.admin_linux.csdevproject.network.retrofit.RetrofitActivityFeedInstance;
import com.example.admin_linux.csdevproject.utils.Constants;
import com.example.admin_linux.csdevproject.utils.DateHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationDetailsActivity extends AppCompatActivity {

    ActivityConversationDetailsBinding mBinding;
    private ConversationDetailsViewModel viewModel;
    private List<ConversationDetailsMessage> mMessageList;
    private String mProfileUrl;
    private String mProfileName;
    int mYourPersonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_details);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_conversation_details);
        mMessageList = new ArrayList<>();

        // Toolbar
        Toolbar toolbar = mBinding.layoutToolbarConversationDetails.toolbarConversationDetails;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        int conversationId = intent.getIntExtra("transfer_conversation_id", 0);
        int personId = intent.getIntExtra("transfer_person_id", 0);
        String bearer = intent.getStringExtra("transfer_bearer");
        mProfileUrl = intent.getStringExtra("transfer_profile_url");
        mProfileName = intent.getStringExtra("transfer_full_name");

        viewModel = ViewModelProviders.of(this).get(ConversationDetailsViewModel.class);

        // Setup participants
        final CDPeopleAdapter mAdapter = new CDPeopleAdapter(this);
        RecyclerView recyclerView = mBinding.layoutToolbarConversationDetails.contentToolbarConversationDetails.rvConversationDetailsToolbar;
        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManagerHorizontal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManagerHorizontal);
        // Set list of people to adapter
        viewModel.getList().observe(this, mAdapter::setConversationDetailsParticipants);

        // Setup messages
        final CDMessageAdapter mMessageAdapter = new CDMessageAdapter(this);
        RecyclerView messageRecyclerView = mBinding.rvActivityConversationDetailsMessages;
        messageRecyclerView.setAdapter(mMessageAdapter);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel.getListOfMessages().observe(this, mMessageAdapter::setConversationDetailsMessages);

        SharedPreferences preferences = getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
        mYourPersonId = preferences.getInt(Constants.PREF_PROFILE_PERSON_ID, 0);

        if (conversationId != 0 && bearer != null) {
            fetchConversationDetails(conversationId, mYourPersonId, personId, bearer);
        }

        setupNotifications();
    }

    private void fetchConversationDetails(int conversationId, int yourPersonId, int personId, String bearer) {
        GetDataService service = RetrofitActivityFeedInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ConversationDetailsReturnValue> parsedJSON = service.getConversationDetail(
                bearer,
                conversationId,
                personId);

        parsedJSON.enqueue(new Callback<ConversationDetailsReturnValue>() {

            @Override
            public void onResponse(@NonNull Call<ConversationDetailsReturnValue> call, @NonNull Response<ConversationDetailsReturnValue> response) {

                ConversationDetailsReturnValue returnValue = response.body();
                CDConversationModel conversationModel = Objects.requireNonNull(returnValue).getCDConversationModel();

                mBinding.tvConversationDetailsDate.setText(DateHelper.returnDate(conversationModel.getLastMessageTime()));
                mBinding.tvConversationDetailsTime.setText(DateHelper.returnTime(conversationModel.getLastMessageTime()));

                CDParticipants participant = null;
                List<CDParticipants> participantList = conversationModel.getParticipants();
                for(CDParticipants iterator : participantList){
                    if(conversationModel.getLastConversationPersonId() == iterator.getPersonId()) {
                        participant = iterator;
                        break;
                    }
                }

                if(participant != null){
                    mMessageList.add(new ConversationDetailsMessage(
                            participant.getPersonImageUrl(),
                            participant.getPersonFullName(),
                            conversationModel.getLastMessageValue()));

                    viewModel.setListOfMessages(mMessageList);
                }

                List<CDParticipants> participantListToFeed = new ArrayList<>();
                for (CDParticipants iterator : participantList){
                    if(iterator.getPersonId() != yourPersonId /*&& iterator.getPersonId() != personId*/){
                        participantListToFeed.add(iterator);
                    }
                }
                viewModel.setList(participantListToFeed);

            }

            @Override
            public void onFailure(@NonNull Call<ConversationDetailsReturnValue> call, @NonNull Throwable t) {
                Log.d("Error: ", t.getMessage());
                Toast.makeText(ConversationDetailsActivity.this, "Oh no... Error fetching conversation details data!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void imgSendMessageOnCDClicked(View view) {
        String messageText = mBinding.etActivityConversationDetailsInputText.getText().toString();
        mMessageList.add(new ConversationDetailsMessage(mProfileUrl, mProfileName, messageText));

        mBinding.etActivityConversationDetailsInputText.setText(null);

        // Hide keyboard when done typing
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mBinding.etActivityConversationDetailsInputText.getWindowToken(), 0);

        viewModel.setListOfMessages(mMessageList);
    }

    public void imgPickPhotoCDClicked(View view) {
        Toast.makeText(this, "Feature is under development", Toast.LENGTH_SHORT).show();
    }

    private void setupNotifications() {
        if (Objects.requireNonNull(getIntent().getExtras()).getBoolean(Constants.NOTIFICATION_INTENT_CONVERSATION_FROM_NOTIFICATION)) {
            String title = getIntent().getExtras().getString(Constants.NOTIFICATION_INTENT_CONVERSATION_TITLE);
            int personId = getIntent().getExtras().getInt(Constants.NOTIFICATION_INTENT_CONVERSATION_PERSON_ID);
            int conversationId = getIntent().getExtras().getInt(Constants.NOTIFICATION_INTENT_CONVERSATION_CONVERSATION_ID);
            SharedPreferences preferences = getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
            String bearer = preferences.getString(Constants.PREF_PROFILE_BEARER, null);
            if(mProfileName == null) mProfileName = preferences.getString(Constants.PREF_PROFILE_FULL_NAME, null);
            if(mProfileUrl == null) mProfileUrl = preferences.getString(Constants.PREF_PROFILE_IMAGE_URL, null);

            if(title != null && personId != 0 && conversationId != 0 && bearer != null && mYourPersonId != 0)  fetchConversationDetails(conversationId, mYourPersonId, personId, bearer);
        }

    }
}
