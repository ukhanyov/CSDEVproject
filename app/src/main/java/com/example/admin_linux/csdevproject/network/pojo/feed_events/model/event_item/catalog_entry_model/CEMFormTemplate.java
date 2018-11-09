package com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.catalog_entry_model;

import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.catalog_entry_model.item_model.CEMFormTemplateItemModelBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CEMFormTemplate {

    @SerializedName("FormTemplateId")
    @Expose
    private int mFormTemplateId;

    @SerializedName("StatusId")
    @Expose
    private String mStatusId;

    @SerializedName("Name")
    @Expose
    private String mName;

    @SerializedName("Description")
    @Expose
    private String mDescription;

    @SerializedName("HideFormHeaderAtBackend")
    @Expose
    private boolean mHideFormHeaderAtBackend;

    @SerializedName("FutterImageUrl")
    @Expose
    private String mFutterImageUrl;

    @SerializedName("OwnerOrganizationId")
    @Expose
    private int mOwnerOrganizationId;

    @SerializedName("FormTemplateItems")
    @Expose
    private List<CEMFormTemplateItemModelBase> mFormTemplateItems;

    public int getFormTemplateId() {
        return mFormTemplateId;
    }

    public String getStatusId() {
        return mStatusId;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public boolean isHideFormHeaderAtBackend() {
        return mHideFormHeaderAtBackend;
    }

    public String getFutterImageUrl() {
        return mFutterImageUrl;
    }

    public int getOwnerOrganizationId() {
        return mOwnerOrganizationId;
    }

    public List<CEMFormTemplateItemModelBase> getFormTemplateItems() {
        return mFormTemplateItems;
    }
}
