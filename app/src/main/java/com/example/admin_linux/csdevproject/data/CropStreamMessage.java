package com.example.admin_linux.csdevproject.data;

import android.os.Parcel;
import android.os.Parcelable;

public class CropStreamMessage implements Parcelable {

    private String mPicture;
    private String mProfileName;
    private String mProfileCorpName;
    private String mMessageText;
    private String mMessageTime;
    private String mMessagePicture;
    private boolean mConversationFirstMessage;
    private String mInvolvedPersonsNames;
    private String mPersonsCorp;
    private boolean mCombineImage;
    private String mCombineImageUrlFirst;
    private String mCombineImageUrlSecond;
    private String mFeedType;
    private boolean mFromOrganization;
    private String mConversationId;
    private String mPersonId;

    public CropStreamMessage(
            String profilePicture,
            String profileName,
            String profileCorpName,
            String messageText,
            String messageTime,
            String messagePicture,
            boolean conversationFirstMessage,
            String involvedPersonsNames,
            String personsCorp,
            boolean combineImage,
            String combineImageUrlFirst,
            String combineImageUrlSecond,
            String feedType,
            boolean isFromOrganization,
            String conversationId,
            String personId) {

        this.mPicture = profilePicture;
        this.mProfileName = profileName;
        this.mProfileCorpName = profileCorpName;
        this.mMessageText = messageText;
        this.mMessageTime = messageTime;
        this.mMessagePicture = messagePicture;
        this.mConversationFirstMessage = conversationFirstMessage;
        this.mInvolvedPersonsNames = involvedPersonsNames;
        this.mPersonsCorp = personsCorp;
        this.mCombineImage = combineImage;
        this.mCombineImageUrlFirst = combineImageUrlFirst;
        this.mCombineImageUrlSecond = combineImageUrlSecond;
        this.mFeedType = feedType;
        this.mFromOrganization = isFromOrganization;
        this.mConversationId = conversationId;
        this.mPersonId = personId;
    }

    public String getProfilePicture() {
        return mPicture;
    }

    public void setProfilePicture(String mProfilePicture) {
        this.mPicture = mProfilePicture;
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

    public boolean getCombineImage() {
        return mCombineImage;
    }

    public void setCombineImage(boolean mCombineImage) {
        this.mCombineImage = mCombineImage;
    }

    public String getCombineImageUrlFirst() {
        return mCombineImageUrlFirst;
    }

    public void setCombineImageUrlFirst(String mCombineImageUrlFirst) {
        this.mCombineImageUrlFirst = mCombineImageUrlFirst;
    }

    public String getCombineImageUrlSecond() {
        return mCombineImageUrlSecond;
    }

    public void setCombineImageUrlSecond(String mCombineImageUrlSecond) {
        this.mCombineImageUrlSecond = mCombineImageUrlSecond;
    }

    public String getFeedType() {
        return mFeedType;
    }

    public void setFeedType(String mFeedType) {
        this.mFeedType = mFeedType;
    }

    public boolean isFromOrganization() {
        return mFromOrganization;
    }

    public void setFromOrganization(boolean mFromOrganization) {
        this.mFromOrganization = mFromOrganization;
    }

    public String getConversationId() {
        return mConversationId;
    }

    public void setConversationId(String mConversationId) {
        this.mConversationId = mConversationId;
    }

    public String getPersonId() {
        return mPersonId;
    }

    public void setPersonId(String mPersonId) {
        this.mPersonId = mPersonId;
    }

    // Parcelling part
    public CropStreamMessage(Parcel in) {
        String[] data = new String[16];
        in.readStringArray(data);

        this.mPicture = data[0];
        this.mProfileName = data[1];
        this.mProfileCorpName = data[2];
        this.mMessageText = data[3];
        this.mMessageTime = data[4];
        this.mMessagePicture = data[5];
        this.mConversationFirstMessage = Boolean.getBoolean(data[6]);
        this.mInvolvedPersonsNames = data[7];
        this.mPersonsCorp = data[8];
        this.mCombineImage = Boolean.getBoolean(data[9]);
        this.mCombineImageUrlFirst = data[10];
        this.mCombineImageUrlSecond = data[11];
        this.mFeedType = data[12];
        this.mFromOrganization = Boolean.getBoolean(data[13]);
        this.mConversationId = data[14];
        this.mPersonId = data[15];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.mPicture,
                this.mProfileName,
                this.mProfileCorpName,
                this.mMessageText,
                this.mMessageTime,
                this.mMessagePicture,
                String.valueOf(this.mConversationFirstMessage),
                this.mInvolvedPersonsNames,
                this.mPersonsCorp,
                String.valueOf(this.mCombineImage),
                this.mCombineImageUrlFirst,
                this.mCombineImageUrlSecond,
                this.mFeedType,
                String.valueOf(this.mFromOrganization),
                this.mConversationId,
                this.mPersonId
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
