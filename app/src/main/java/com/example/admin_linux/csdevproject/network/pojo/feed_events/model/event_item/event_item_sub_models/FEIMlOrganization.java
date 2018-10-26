package com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.event_item_sub_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FEIMlOrganization {

    @SerializedName("OrganizationId")
    @Expose
    private int organizationId;

    @SerializedName("OrganizationName")
    @Expose
    private String organizationName;

    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;

    public int getOrganizationId() {
        return organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
