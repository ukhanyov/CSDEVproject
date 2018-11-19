package com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.favorite_form_template;

import com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.favorite_form_template.template_model.FFTFormTemplateModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoriteFormTemplate {

    @SerializedName("FavoriteCatalogGroupId")
    @Expose
    private int mFavoriteCatalogGroupId;

    @SerializedName("GroupName")
    @Expose
    private String mGroupName;

    @SerializedName("GroupDisplayOrder")
    @Expose
    private int mGroupDisplayOrder;

    @SerializedName("FormTemplateId")
    @Expose
    private int mFormTemplateId;

    @SerializedName("ItemTemplateId")
    @Expose
    private int mItemTemplateId;

    @SerializedName("FormType")
    @Expose
    private String mFormType;

    @SerializedName("FormName")
    @Expose
    private String mFormName;

    @SerializedName("FormTypeId")
    @Expose
    private int mFormTypeId;

    @SerializedName("ConversationUserId")
    @Expose
    private int mConversationUserId;

    @SerializedName("FavoriteCatalogEntryId")
    @Expose
    private int mFavoriteCatalogEntryId;

    @SerializedName("CatalogEntryId")
    @Expose
    private int mCatalogEntryId;

    @SerializedName("EntryDisplayOrder")
    @Expose
    private int mEntryDisplayOrder;

    @SerializedName("FormDescription")
    @Expose
    private String mFormDescription;

    @SerializedName("FormTemplateModel")
    @Expose
    private FFTFormTemplateModel mFormTemplateModel;

    @SerializedName("FormOwnerOrganizationId")
    @Expose
    private int mFormOwnerOrganizationId;

    @SerializedName("FormOwnerOrganizationName")
    @Expose
    private String mFormOwnerOrganizationName;

    @SerializedName("FormOwnerOrganizationCity")
    @Expose
    private String mFormOwnerOrganizationCity;

    @SerializedName("FormOwnerOrganizationState")
    @Expose
    private String mFormOwnerOrganizationState;

    @SerializedName("IsFavorite")
    @Expose
    private boolean mIsFavorite;

    public int getFavoriteCatalogGroupId() {
        return mFavoriteCatalogGroupId;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public int getGroupDisplayOrder() {
        return mGroupDisplayOrder;
    }

    public int getFormTemplateId() {
        return mFormTemplateId;
    }

    public int getItemTemplateId() {
        return mItemTemplateId;
    }

    public String getFormType() {
        return mFormType;
    }

    public String getFormName() {
        return mFormName;
    }

    public int getFormTypeId() {
        return mFormTypeId;
    }

    public int getConversationUserId() {
        return mConversationUserId;
    }

    public int getFavoriteCatalogEntryId() {
        return mFavoriteCatalogEntryId;
    }

    public int getCatalogEntryId() {
        return mCatalogEntryId;
    }

    public int getEntryDisplayOrder() {
        return mEntryDisplayOrder;
    }

    public String getFormDescription() {
        return mFormDescription;
    }

    public FFTFormTemplateModel getFormTemplateModel() {
        return mFormTemplateModel;
    }

    public int getFormOwnerOrganizationId() {
        return mFormOwnerOrganizationId;
    }

    public String getFormOwnerOrganizationName() {
        return mFormOwnerOrganizationName;
    }

    public String getFormOwnerOrganizationCity() {
        return mFormOwnerOrganizationCity;
    }

    public String getFormOwnerOrganizationState() {
        return mFormOwnerOrganizationState;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }
}
