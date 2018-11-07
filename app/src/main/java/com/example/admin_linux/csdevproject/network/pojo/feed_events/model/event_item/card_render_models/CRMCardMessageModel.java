package com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.card_render_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CRMCardMessageModel {

    @SerializedName("Message")
    @Expose
    private String mMessage;

    @SerializedName("AspectRatio")
    @Expose
    private double mAspectRatio;

    @SerializedName("MessageType")
    @Expose
    private String mMessageType;

    @SerializedName("ProcessLinks")
    @Expose
    private boolean mProcessLinks;

    public String getMessage() {
        return mMessage;
    }

    public double getAspectRatio() {
        return mAspectRatio;
    }

    public String getMessageType() {
        return mMessageType;
    }

    public boolean isProcessLinks() {
        return mProcessLinks;
    }
}
