package com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item;

import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.catalog_entry_model.CEMFormTemplate;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedEventCatalogEntries {

    @SerializedName("CatalogEntryId")
    @Expose
    private int mCatalogEntryId;

    @SerializedName("FormTemplateId")
    @Expose
    private int mFormTemplateId;

    @SerializedName("FormTemplateModel")
    @Expose
    private CEMFormTemplate mFormTemplateModel;

    public int getCatalogEntryId() {
        return mCatalogEntryId;
    }

    public int getFormTemplateId() {
        return mFormTemplateId;
    }

    public CEMFormTemplate getFormTemplateModel() {
        return mFormTemplateModel;
    }
}
