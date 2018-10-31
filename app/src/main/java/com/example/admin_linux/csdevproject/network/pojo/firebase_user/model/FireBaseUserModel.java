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

    public String getAuthorizeToken() {
        return mAuthorizeToken;
    }

    public int getPersonId() {
        return mPersonId;
    }

    public int getUserId() {
        return mUserId;
    }
}
