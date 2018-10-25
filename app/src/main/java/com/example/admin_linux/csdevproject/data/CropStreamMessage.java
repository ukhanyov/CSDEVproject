package com.example.admin_linux.csdevproject.data;

import android.os.Parcel;
import android.os.Parcelable;

public class CropStreamMessage implements Parcelable {

    private String mProfilePicture;
    private String mProfileName;
    private String mProfileCorpName;
    private String mMessageDestination;
    private String mMessageText;
    private String mMessageTime;
    private String mMessagePicture;

    public CropStreamMessage(
            String profilePicture,
            String profileName,
            String profileCorpName,
            String messageDestination,
            String messageText,
            String messageTime,
            String messagePicture) {

        this.mProfilePicture = profilePicture;
        this.mProfileName = profileName;
        this.mProfileCorpName = profileCorpName;
        this.mMessageDestination = messageDestination;
        this.mMessageText = messageText;
        this.mMessageTime = messageTime;
        this.mMessagePicture = messagePicture;
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

    public String getMessageDestination() {
        return mMessageDestination;
    }

    public void setMessageDestination(String mMessageDestination) {
        this.mMessageDestination = mMessageDestination;
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

    // Parcelling part
    public CropStreamMessage(Parcel in) {
        String[] data = new String[7];
        in.readStringArray(data);

        this.mProfilePicture = data[0];
        this.mProfileName = data[1];
        this.mProfileCorpName = data[2];
        this.mMessageDestination = data[3];
        this.mMessageText = data[4];
        this.mMessageTime = data[5];
        this.mMessagePicture = data[6];
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
                this.mMessageDestination,
                this.mMessageText,
                this.mMessageTime,
                this.mMessagePicture
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
