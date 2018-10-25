package com.example.admin_linux.csdevproject.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedEventsModel {

    @SerializedName("FeedEvents")
    @Expose
    private FeedEventItemModel mFeedEventItemModel;

    public FeedEventItemModel getFeedEventItemModel() {
        return mFeedEventItemModel;
    }
}
