package com.example.admin_linux.csdevproject.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ConversationDetailsMessage implements Parcelable {

    private String mProfilePictureUrl;
    private String mProfileName;
    private String mMessage;

    public ConversationDetailsMessage(String profilePictureUrl, String profileName, String message) {
        this.mProfilePictureUrl = profilePictureUrl;
        this.mProfileName = profileName;
        this.mMessage = message;
    }

    public String getProfilePictureUrl() {
        return mProfilePictureUrl;
    }

    public String getProfileName() {
        return mProfileName;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    // Parcelling part
    public ConversationDetailsMessage(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);

        this.mProfilePictureUrl = data[0];
        this.mProfileName = data[1];
        this.mMessage = data[2];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.mProfilePictureUrl,
                this.mProfileName,
                this.mMessage,
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ConversationDetailsMessage createFromParcel(Parcel in) {
            return new ConversationDetailsMessage(in);
        }

        public ConversationDetailsMessage[] newArray(int size) {
            return new ConversationDetailsMessage[size];
        }
    };
}
