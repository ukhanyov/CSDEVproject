package com.example.admin_linux.csdevproject.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

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
    private boolean mConversationChat;
    private String mPersonPictureUrl;
    private int mCardRenderDataId;
    private String mMessageHttp;
    private double mAspectRatio;
    private String mMessageType;
    private int mCatalogEntryId;
    private List<TemplateItemModelBase> mTemplateItemModelBaseList;
    private String mTemplateModelName;
    private String mTemplateModelDescription;

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
            String personId,
            boolean conversationChat,
            String personPictureUrl,
            int cardRenderDataId,
            String messageHttp,
            double aspectRatio,
            String messageType,
            int catalogEntryId,
            List<TemplateItemModelBase> templateItemModelBaseList,
            String templateModelName,
            String templateModelDescription) {

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
        this.mConversationChat = conversationChat;
        this.mPersonPictureUrl = personPictureUrl;
        this.mCardRenderDataId = cardRenderDataId;
        this.mMessageHttp = messageHttp;
        this.mAspectRatio = aspectRatio;
        this.mMessageType = messageType;
        this.mCatalogEntryId = catalogEntryId;
        this.mTemplateItemModelBaseList = templateItemModelBaseList;
        this.mTemplateModelName = templateModelName;
        this.mTemplateModelDescription = templateModelDescription;
    }

    public String getProfilePicture() {
        return mPicture;
    }

    public String getProfileName() {
        return mProfileName;
    }

    public String getProfileCorpName() {
        return mProfileCorpName;
    }

    public String getMessageText() {
        return mMessageText;
    }

    public String getMessageTime() {
        return mMessageTime;
    }

    public String getMessagePicture() {
        return mMessagePicture;
    }

    public boolean getConversationFirstMessage() {
        return mConversationFirstMessage;
    }

    public String getInvolvedPersonsNames() {
        return mInvolvedPersonsNames;
    }

    public String getPersonsCorp() {
        return mPersonsCorp;
    }

    public boolean getCombineImage() {
        return mCombineImage;
    }

    public String getCombineImageUrlFirst() {
        return mCombineImageUrlFirst;
    }

    public String getCombineImageUrlSecond() {
        return mCombineImageUrlSecond;
    }

    public String getFeedType() {
        return mFeedType;
    }

    public boolean isFromOrganization() {
        return mFromOrganization;
    }

    public String getConversationId() {
        return mConversationId;
    }

    public String getPersonId() {
        return mPersonId;
    }

    public boolean getConversationChat() {
        return mConversationChat;
    }

    public String getPersonPictureUrl() {
        return mPersonPictureUrl;
    }

    public int getCardRenderDataId() {
        return mCardRenderDataId;
    }

    public String getMessageHttp() {
        return mMessageHttp;
    }

    public double getAspectRatio() { return mAspectRatio; }

    public String getMessageType() {
        return mMessageType;
    }

    public List<TemplateItemModelBase> getTemplateItemModelBaseList() {
        return mTemplateItemModelBaseList;
    }

    public String getTemplateModelName() {
        return mTemplateModelName;
    }

    public String getTemplateModelDescription() {
        return mTemplateModelDescription;
    }

    // Parcelling part
    public CropStreamMessage(Parcel in) {
        String[] data = new String[25];
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
        this.mConversationChat = Boolean.getBoolean(data[16]);
        this.mPersonPictureUrl = data[17];
        this.mCardRenderDataId = Integer.valueOf(data[18]);
        this.mMessageHttp = data[19];
        this.mAspectRatio = Double.valueOf(data[20]);
        this.mCatalogEntryId = Integer.valueOf(data[21]);
        this.mMessageType = data[22];
        this.mTemplateModelName = data[23];
        this.mTemplateModelDescription = data[24];

        List<TemplateItemModelBase> list = new ArrayList<>();
        in.readTypedList(list, TemplateItemModelBase.CREATOR);

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
                this.mPersonId,
                String.valueOf(this.mConversationChat),
                this.mPersonPictureUrl,
                String.valueOf(this.mCardRenderDataId),
                this.mMessageHttp,
                String.valueOf(this.mAspectRatio),
                String.valueOf(this.mCatalogEntryId),
                this.mMessageType,
                this.mTemplateModelName,
                this.mTemplateModelDescription
        });
        dest.writeTypedList(this.mTemplateItemModelBaseList);
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
