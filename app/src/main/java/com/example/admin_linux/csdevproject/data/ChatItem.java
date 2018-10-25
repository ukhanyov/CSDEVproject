package com.example.admin_linux.csdevproject.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ChatItem implements Parcelable {

    private String mProfilePicture;
    private String mProfileName;
    private String mMessageText;
    private String mMessageTime;

    public ChatItem(String profilePicture, String profileName, String messageText, String messageTime) {
        this.mProfilePicture = profilePicture;
        this.mProfileName = profileName;
        this.mMessageText = messageText;
        this.mMessageTime = messageTime;
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

    // Parcelling part
    public ChatItem(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);

        this.mProfilePicture = data[0];
        this.mProfileName = data[1];
        this.mMessageText = data[2];
        this.mMessageTime = data[3];
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
                this.mMessageText,
                this.mMessageTime
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ChatItem createFromParcel(Parcel in) {
            return new ChatItem(in);
        }

        public ChatItem[] newArray(int size) {
            return new ChatItem[size];
        }
    };
}
