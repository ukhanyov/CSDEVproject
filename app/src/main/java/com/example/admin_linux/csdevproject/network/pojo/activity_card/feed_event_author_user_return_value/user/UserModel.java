package com.example.admin_linux.csdevproject.network.pojo.activity_card.feed_event_author_user_return_value.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("UserId")
    @Expose
    private int mUserId;

    public int geUserModeltUserId() {
        return mUserId;
    }
}
