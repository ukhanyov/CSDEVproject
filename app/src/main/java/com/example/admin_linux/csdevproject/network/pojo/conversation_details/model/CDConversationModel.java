package com.example.admin_linux.csdevproject.network.pojo.conversation_details.model;

import com.example.admin_linux.csdevproject.network.pojo.conversation_details.model.participants.CDParticipants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CDConversationModel {

    /*

    "ConversationId": 1,
    "LastConversationPersonId": 1,
    "ActivityId": 1,
    "Title": "sample string 2",
    "MessagesCount": 3,
    "LastMessageType": 1,
    "LastMessageValue": "sample string 4",
    "Status": 1,
    "LastMessageTime": {},
    "CreatorPersonId": 1,
    "LayerKey": "sample string 5",
    "CatalogEntryId": 1,
    "Participants"

     */

    @SerializedName("ConversationId")
    @Expose
    private int mConversationId;

    @SerializedName("LastConversationPersonId")
    @Expose
    private int mLastConversationPersonId;
//
//    @SerializedName("ActivityId")
//    @Expose
//    private int mActivityId;

    @SerializedName("Title")
    @Expose
    private String mTitle;

//    @SerializedName("MessagesCount")
//    @Expose
//    private int mMessagesCount;
//
//    @SerializedName("LastMessageType")
//    @Expose
//    private int mLastMessageType;

    @SerializedName("LastMessageValue")
    @Expose
    private String mLastMessageValue;

    @SerializedName("LastMessageTime")
    @Expose
    private String mLastMessageTime;

    @SerializedName("Participants")
    @Expose
    private List<CDParticipants> mParticipants;

    public int getConversationId() {
        return mConversationId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getLastMessageValue() {
        return mLastMessageValue;
    }

    public List<CDParticipants> getParticipants() {
        return mParticipants;
    }

    public int getLastConversationPersonId() {
        return mLastConversationPersonId;
    }

    public String getLastMessageTime() {
        return mLastMessageTime;
    }
}
