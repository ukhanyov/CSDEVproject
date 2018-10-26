package com.example.admin_linux.csdevproject.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CropStreamMessage implements Parcelable {

    private String mProfilePicture;
    private String mProfileName;
    private String mProfileCorpName;
    private String mMessageText;
    private String mMessageTime;
    private String mMessagePicture;
    private boolean mConversationFirstMessage;
    private String mInvolvedPersonsNames;
    private String mPersonsCorp;

    public CropStreamMessage(
            String profilePicture,
            String profileName,
            String profileCorpName,
            String messageText,
            String messageTime,
            String messagePicture,
            boolean conversationFirstMessage,
            String involvedPersonsNames,
            String personsCorp) {

        this.mProfilePicture = profilePicture;
        this.mProfileName = profileName;
        this.mProfileCorpName = profileCorpName;
        this.mMessageText = messageText;
        this.mMessageTime = messageTime;
        this.mMessagePicture = messagePicture;
        this.mConversationFirstMessage = conversationFirstMessage;
        this.mInvolvedPersonsNames = involvedPersonsNames;
        this.mPersonsCorp = personsCorp;
    }

    public String getProfilePicture() {
        return mProfilePicture;
    }

    public void setProfilePicture(String mProfilePicture) {
        this.mProfilePicture = mProfilePicture;
    }

    public String getProfileName() {
        return mProfileName;
    }

    public void setProfileName(String mProfileName) {
        this.mProfileName = mProfileName;
    }

    public String getProfileCorpName() {
        return mProfileCorpName;
    }

    public void setProfileCorpName(String mProfileCorpName) {
        this.mProfileCorpName = mProfileCorpName;
    }

    public String getMessageText() {
        return mMessageText;
    }

    public void setMessageText(String mMessageText) {
        this.mMessageText = mMessageText;
    }

    public String getMessageTime() {
        return mMessageTime;
    }

    public void setMessageTime(String mMessageTime) {
        this.mMessageTime = mMessageTime;
    }

    public String getMessagePicture() {
        return mMessagePicture;
    }

    public void setMessagePicture(String mMessagePicture) {
        this.mMessagePicture = mMessagePicture;
    }

    public boolean getConversationFirstMessage() {
        return mConversationFirstMessage;
    }

    public void setConversationFirstMessage(boolean mConversationFirstMessage) {
        this.mConversationFirstMessage = mConversationFirstMessage;
    }

    public String getInvolvedPersonsNames() {
        return mInvolvedPersonsNames;
    }

    public void setInvolvedPersonsNames(String mInvolvedPersonsNames) {
        this.mInvolvedPersonsNames = mInvolvedPersonsNames;
    }

    public String getPersonsCorp() {
        return mPersonsCorp;
    }

    public void setPersonsCorp(String mPersonsCorp) {
        this.mPersonsCorp = mPersonsCorp;
    }

    // Parcelling part
    public CropStreamMessage(Parcel in) {
        String[] data = new String[9];
        in.readStringArray(data);

        this.mProfilePicture = data[0];
        this.mProfileName = data[1];
        this.mProfileCorpName = data[2];
        this.mMessageText = data[3];
        this.mMessageTime = data[4];
        this.mMessagePicture = data[5];
        this.mConversationFirstMessage = Boolean.getBoolean(data[6]);
        this.mInvolvedPersonsNames = data[7];
        this.mPersonsCorp = data[8];

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.mProfilePicture,
                this.mProfileName,
                this.mProfileCorpName,
                this.mMessageText,
                this.mMessageTime,
                this.mMessagePicture,
                String.valueOf(this.mConversationFirstMessage),
                this.mInvolvedPersonsNames,
                this.mPersonsCorp
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CropStreamMessage createFromParcel(Parcel in) {
            return new CropStreamMessage(in);
        }

        public CropStreamMessage[] newArray(int size) {
            return new CropStreamMessage[size];
        }
    };
}
