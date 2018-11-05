package com.example.admin_linux.csdevproject.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ConversationPerson implements Parcelable {

    private int mPersonId;
    private String mPersonFirstName;
    private String mPersonLastName;
    private String mPersonFullName;
    private boolean mIsFavorite;
    private String mOrganizationName;
    private String mIconPath;
    private String mMessageText;

    public ConversationPerson(int mPersonId,
                              String personFirstName,
                              String personLastName,
                              String personFullName,
                              boolean isFavorite,
                              String organizationName,
                              String iconPath,
                              String messageText) {
        this.mPersonId = mPersonId;
        this.mPersonFirstName = personFirstName;
        this.mPersonLastName = personLastName;
        this.mPersonFullName = personFullName;
        this.mIsFavorite = isFavorite;
        this.mOrganizationName = organizationName;
        this.mIconPath = iconPath;
        this.mMessageText = messageText;
    }

    public int getPersonId() {
        return mPersonId;
    }

    public String getPersonFirstName() {
        return mPersonFirstName;
    }

    public String getPersonLastName() {
        return mPersonLastName;
    }

    public String getPersonFullName() {
        return mPersonFullName;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public String getOrganizationName() {
        return mOrganizationName;
    }

    public String getIconPath() {
        return mIconPath;
    }

    public String getMessageText() {
        return mMessageText;
    }

    // Parcelling part
    public ConversationPerson(Parcel in) {
        String[] data = new String[8];
        in.readStringArray(data);

        this.mPersonId = Integer.valueOf(data[0]);
        this.mPersonFirstName = data[1];
        this.mPersonLastName = data[2];
        this.mPersonFullName = data[3];
        this.mIsFavorite = Boolean.getBoolean(data[4]);
        this.mOrganizationName = data[5];
        this.mIconPath = data[6];
        this.mMessageText = data[7];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                String.valueOf(this.mPersonId),
                this.mPersonFirstName,
                this.mPersonLastName,
                this.mPersonFullName,
                String.valueOf(this.mIsFavorite),
                this.mOrganizationName,
                this.mIconPath,
                this.mMessageText
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public ConversationPerson createFromParcel(Parcel in) { return new ConversationPerson(in); }

        public ConversationPerson[] newArray(int size) { return new ConversationPerson[size]; }
    };
}
