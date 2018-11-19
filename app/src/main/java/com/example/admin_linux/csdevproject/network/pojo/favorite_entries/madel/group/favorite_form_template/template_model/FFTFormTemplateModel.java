package com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.favorite_form_template.template_model;

import com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.favorite_form_template.template_model.model_base.FFTFormTemplateItemModelBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FFTFormTemplateModel {

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
    private List<FFTFormTemplateItemModelBase> mFormTemplateItems;

    public int getFormTemplateId() {
        return mFormTemplateId;
    }

    public String getFormTemplateModelStatusId() {
        return mStatusId;
    }

    public String getFormTemplateModelName() {
        return mName;
    }

    public String getFormTemplateModelDescription() {
        return mDescription;
    }

    public boolean isFormTemplateModelHideFormHeaderAtBackend() {
        return mHideFormHeaderAtBackend;
    }

    public String getFormTemplateModelFutterImageUrl() {
        return mFutterImageUrl;
    }

    public int getFormTemplateModelOwnerOrganizationId() {
        return mOwnerOrganizationId;
    }

    public List<FFTFormTemplateItemModelBase> getFormTemplateItems() {
        return mFormTemplateItems;
    }
}
