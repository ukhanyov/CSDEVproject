package com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.event_item_sub_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedEventItemModelFeedImage {

    @SerializedName("Url")
    @Expose
    private String url;

    @SerializedName("Width")
    @Expose
    private int width;

    @SerializedName("Height")
    @Expose
    private int height;

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
