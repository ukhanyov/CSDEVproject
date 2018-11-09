package com.example.admin_linux.csdevproject.network.pojo.feed_events.model;

import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.FeedEventCatalogEntries;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.FeedEventItemModel;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.FeedEventCardRenderItems;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedEventsModel {

    @SerializedName("FeedEvents")
    @Expose
    private List<FeedEventItemModel> mFeedEventItemModel;

    @SerializedName("CatalogEntries")
    @Expose
    private List<FeedEventCatalogEntries> mCatalogEntries;

    @SerializedName("CardRenderItems")
    @Expose
    private List<FeedEventCardRenderItems> mCardRenderItems;

    public List<FeedEventItemModel> getFeedEventItemModels() {
        return mFeedEventItemModel;
    }

    public List<FeedEventCatalogEntries> getCatalogEntries() {
        return mCatalogEntries;
    }

    public List<FeedEventCardRenderItems> getCardRenderItems() {
        return mCardRenderItems;
    }
}
