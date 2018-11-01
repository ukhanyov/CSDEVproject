package com.example.admin_linux.csdevproject.network.pojo.conversation_details;

import com.example.admin_linux.csdevproject.network.pojo.conversation_details.model.CDConversationModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConversationDetailsReturnValue {

    @SerializedName("ReturnValue")
    @Expose
    private CDConversationModel mCDConversationModel;

    public CDConversationModel getCDConversationModel() {
        return mCDConversationModel;
    }
}
