package com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.catalog_entry_model.item_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CEMFormTemplateItemModelBase {

    @SerializedName("ItemType")
    @Expose
    private String mItemType;

    @SerializedName("Label")
    @Expose
    private String mLabel;

    @SerializedName("ResourceUrl")
    @Expose
    private String mResourceUrl;

    @SerializedName("InnerHtml")
    @Expose
    private String mInnerHtml;

    public String getItemType() {
        return mItemType;
    }

    public String getLabel() {
        return mLabel;
    }

    public String getResourceUrl() {
        return mResourceUrl;
    }

    public String getInnerHtml() {
        return mInnerHtml;
    }
}
