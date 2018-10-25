package com.example.admin_linux.csdevproject.network.pojo.feed_events.model;

import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.FeedEventItemModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedEventsModel {

    @SerializedName("FeedEvents")
    @Expose
    private List<FeedEventItemModel> mFeedEventItemModel;

    public List<FeedEventItemModel> getFeedEventItemModels() {
        return mFeedEventItemModel;
    }
}
