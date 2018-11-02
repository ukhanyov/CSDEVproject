package com.example.admin_linux.csdevproject.data;

import android.os.Parcelable;

public class ConversationPerson {
    /*

    "PersonId": 232046,
                    "PersonFirstName": "Alice",
                    "PersonLastName": "Dennis",
                    "PersonFullName": "Alice Dennis",
                    "IsFavorite": false,
                    "OrganizationName": "Ag-Bid",
                    "IconPath": "https://dev.cropstream.com/Image/GetProfilePicture?ImageName=06_c8ea970e-b961-4f0c-9cc4-e5b8edf33092.jpg&width=140&height=140"
     */

    private int mPersonId;
    private String mPersonFirstName;
    private String mPersonLastName;
    private String mPersonFullName;
    private boolean mIsFavorite;
    private String mOrganizationName;
    private String mIconPath;

    public ConversationPerson(int mPersonId, String personFirstName, String personLastName, String personFullName, boolean isFavorite, String organizationName, String iconPath) {
        this.mPersonId = mPersonId;
        this.mPersonFirstName = personFirstName;
        this.mPersonLastName = personLastName;
        this.mPersonFullName = personFullName;
        this.mIsFavorite = isFavorite;
        this.mOrganizationName = organizationName;
        this.mIconPath = iconPath;
    }

    public int getPersonId() {
        return mPersonId;
    }

    public void setPersonId(int mPersonId) {
        this.mPersonId = mPersonId;
    }

    public String getPersonFirstName() {
        return mPersonFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        mPersonFirstName = personFirstName;
    }

    public String getPersonLastName() {
        return mPersonLastName;
    }

    public void setPersonLastName(String personLastName) {
        mPersonLastName = personLastName;
    }

    public String getPersonFullName() {
        return mPersonFullName;
    }

    public void setPersonFullName(String personFullName) {
        mPersonFullName = personFullName;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean favorite) {
        mIsFavorite = favorite;
    }

    public String getOrganizationName() {
        return mOrganizationName;
    }

    public void setOrganizationName(String organizationName) {
        mOrganizationName = organizationName;
    }

    public String getIconPath() {
        return mIconPath;
    }

    public void setIconPath(String iconPath) {
        mIconPath = iconPath;
    }
}
