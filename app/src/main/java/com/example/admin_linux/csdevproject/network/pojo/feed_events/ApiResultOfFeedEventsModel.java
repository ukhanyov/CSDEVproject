package com.example.admin_linux.csdevproject.network.pojo.feed_events;

import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.FeedEventsModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResultOfFeedEventsModel {

    @SerializedName("ReturnValue")
    @Expose
    private FeedEventsModel mFeedEventsModel;

    public FeedEventsModel getFeedEventsModel() {
        return mFeedEventsModel;
    }
}
