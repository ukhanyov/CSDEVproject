package com.example.admin_linux.csdevproject.network.pojo.firebase_user.model;

import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.FeedEventsModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FireBaseUserModel {

    @SerializedName("AuthorizeToken")
    @Expose
    private String mAuthorizeToken;

    @SerializedName("PersonId")
    @Expose
    private int mPersonId;

    @SerializedName("UserId")
    @Expose
    private int mUserId;

    @SerializedName("FirstName")
    @Expose
    private String mFirstName;

    @SerializedName("LastName")
    @Expose
    private String mLastName;

    @SerializedName("ProfileImageUrl")
    @Expose
    private String mProfileImageUrl;

    public String getAuthorizeToken() {
        return mAuthorizeToken;
    }

    public int getPersonId() {
        return mPersonId;
    }

    public int getUserId() {
        return mUserId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getProfileImageUrl() {
        return mProfileImageUrl;
    }
}
