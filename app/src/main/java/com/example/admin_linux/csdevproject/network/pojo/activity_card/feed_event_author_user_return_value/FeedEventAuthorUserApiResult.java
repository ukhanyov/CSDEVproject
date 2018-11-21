package com.example.admin_linux.csdevproject.network.pojo.activity_card.feed_event_author_user_return_value;

import com.example.admin_linux.csdevproject.network.pojo.activity_card.feed_event_author_user_return_value.user.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedEventAuthorUserApiResult {

    @SerializedName("User")
    @Expose
    private UserModel mUser;

    public UserModel getFeedEventAuthorUserApiResultUser() {
        return mUser;
    }
}
