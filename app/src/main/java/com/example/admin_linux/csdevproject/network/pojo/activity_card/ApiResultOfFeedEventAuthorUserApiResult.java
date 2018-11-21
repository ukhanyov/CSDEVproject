package com.example.admin_linux.csdevproject.network.pojo.activity_card;

import com.example.admin_linux.csdevproject.network.pojo.activity_card.feed_event_author_user_return_value.FeedEventAuthorUserApiResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResultOfFeedEventAuthorUserApiResult {

    @SerializedName("ReturnValue")
    @Expose
    private FeedEventAuthorUserApiResult mReturnValue;

    public FeedEventAuthorUserApiResult getApiResultOfFeedEventAuthorUserApiResultReturnValue() {
        return mReturnValue;
    }
}
