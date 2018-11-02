package com.example.admin_linux.csdevproject;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
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
import com.example.admin_linux.csdevproject.data.ConversationDetailsMesasge;
import com.example.admin_linux.csdevproject.data.ConversationDetailsViewModel;
import com.example.admin_linux.csdevproject.data.CropStreamMessage;
import com.example.admin_linux.csdevproject.databinding.ActivityConversationDetailsBinding;
import com.example.admin_linux.csdevproject.network.pojo.conversation_details.ConversationDetailsReturnValue;
import com.example.admin_linux.csdevproject.network.pojo.conversation_details.model.CDConversationModel;
import com.example.admin_linux.csdevproject.network.pojo.conversation_details.model.participants.CDParticipants;
import com.example.admin_linux.csdevproject.network.retrofit.GetDataService;
import com.example.admin_linux.csdevproject.network.retrofit.RetrofitActivityFeedInstance;
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
    private List<ConversationDetailsMesasge> mMessageList;
    private String mMessageText;
    private String mProfileUrl;
    private String mProfileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_details);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_conversation_details);

        // Toolbar
        Toolbar toolbar = mBinding.layoutToolbarConversationDetails.toolbarConversationDetails;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        CropStreamMessage cropStreamMessage = intent.getParcelableExtra("transfer_message");
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

        if (cropStreamMessage != null && bearer != null) {
            fetchConversationDetails(cropStreamMessage, bearer);
        }
    }

    private void fetchConversationDetails(CropStreamMessage cropStreamMessage, String bearer) {
        GetDataService service = RetrofitActivityFeedInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ConversationDetailsReturnValue> parsedJSON = service.geConversationDetail(
                bearer,
                Integer.valueOf(cropStreamMessage.getConversationId()),
                Integer.valueOf(cropStreamMessage.getPersonId()));

        parsedJSON.enqueue(new Callback<ConversationDetailsReturnValue>() {
            @Override
            public void onResponse(@NonNull Call<ConversationDetailsReturnValue> call, @NonNull Response<ConversationDetailsReturnValue> response) {
                ConversationDetailsReturnValue returnValue = response.body();
                CDConversationModel conversationModel = Objects.requireNonNull(returnValue).getCDConversationModel();

                mBinding.tvConversationDetailsDate.setText(DateHelper.returnDate(conversationModel.getLastMessageTime()));
                mBinding.tvConversationDetailsTime.setText(DateHelper.returnTime(conversationModel.getLastMessageTime()));

                // Populate viewModel
                viewModel.setList(conversationModel.getParticipants());

                CDParticipants participant = null;
                List<CDParticipants> participantList = conversationModel.getParticipants();
                for(CDParticipants iterator : participantList){
                    if(conversationModel.getLastConversationPersonId() == iterator.getPersonId()) {
                        participant = iterator;
                        break;
                    }
                }

                if(participant != null){
                    mMessageList = new ArrayList<>();
                    mMessageList.add(new ConversationDetailsMesasge(
                            participant.getPersonImageUrl(),
                            participant.getPersonFullName(),
                            conversationModel.getLastMessageValue()));

                    viewModel.setListOfMessages(mMessageList);
                }

            }

            @Override
            public void onFailure(@NonNull Call<ConversationDetailsReturnValue> call, @NonNull Throwable t) {
                Log.d("Error: ", t.getMessage());
                Toast.makeText(ConversationDetailsActivity.this, "Oh no... Error fetching conversation details data!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void imgSendMessageOnCDClicked(View view) {
        mMessageText = mBinding.etActivityConversationDetailsInputText.getText().toString();
        mMessageList.add(new ConversationDetailsMesasge(mProfileUrl, mProfileName, mMessageText));

        mBinding.etActivityConversationDetailsInputText.setText(null);

        // Hide keyboard when done typing
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mBinding.etActivityConversationDetailsInputText.getWindowToken(), 0);

        viewModel.setListOfMessages(mMessageList);
    }

    public void imgPickPhotoCDClicked(View view) {
        Toast.makeText(this, "Feature is under development", Toast.LENGTH_SHORT).show();
    }
}
