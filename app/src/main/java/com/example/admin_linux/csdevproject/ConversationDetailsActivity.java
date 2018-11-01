package com.example.admin_linux.csdevproject;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.admin_linux.csdevproject.data.CropStreamMessage;
import com.example.admin_linux.csdevproject.databinding.ActivityConversationDetailsBinding;
import com.example.admin_linux.csdevproject.network.pojo.conversation_details.ConversationDetailsReturnValue;
import com.example.admin_linux.csdevproject.network.pojo.conversation_details.model.CDConversationModel;
import com.example.admin_linux.csdevproject.network.pojo.firebase_user.FirebaseUserReturnValue;
import com.example.admin_linux.csdevproject.network.pojo.firebase_user.model.FireBaseUserModel;
import com.example.admin_linux.csdevproject.network.retrofit.GetDataService;
import com.example.admin_linux.csdevproject.network.retrofit.RetrofitActivityFeedInstance;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationDetailsActivity extends AppCompatActivity {

    ActivityConversationDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_details);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_conversation_details);

        Intent intent = getIntent();
        CropStreamMessage cropStreamMessage = intent.getParcelableExtra("transfer_message");
        String bearer = intent.getStringExtra("transfer_bearer");
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

                mBinding.tvActivityConversationDetailsLastMessage.setText(conversationModel.getLastMessageValue());
            }

            @Override
            public void onFailure(@NonNull Call<ConversationDetailsReturnValue> call, @NonNull Throwable t) {
                Log.d("Error: ", t.getMessage());
                Toast.makeText(ConversationDetailsActivity.this, "Oh no... Error fetching conversation details data!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}