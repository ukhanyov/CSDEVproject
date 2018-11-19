package com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.favorite_form_template.template_model.model_base;

import com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.favorite_form_template.template_model.model_base.items.FFTFormTemplateItemKeyValue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FFTFormTemplateItemModelBase {

    @SerializedName("ItemType")
    @Expose
    private String mItemType;

    @SerializedName("DisplayOrder")
    @Expose
    private int mDisplayOrder;

    @SerializedName("Label")
    @Expose
    private String mLabel;

    @SerializedName("ItemId")
    @Expose
    private String mItemId;

    @SerializedName("ItemFieldID")
    @Expose
    private String mItemFieldID;

    @SerializedName("Placeholder")
    @Expose
    private String mPlaceholder;

    @SerializedName("ResourceUrl")
    @Expose
    private String mResourceUrl;

    @SerializedName("SelectedLabel")
    @Expose
    private String mSelectedLabel;

    @SerializedName("UnSelectedLabel")
    @Expose
    private String mUnSelectedLabel;

    @SerializedName("Items")
    @Expose
    private List<FFTFormTemplateItemKeyValue> Items;

    @SerializedName("ColumnsCount")
    @Expose
    private int mColumnsCount;

    @SerializedName("RowsCount")
    @Expose
    private int mRowsCount;

    @SerializedName("DateItemSubType")
    @Expose
    private String mDateItemSubType;

    @SerializedName("TextBoxItemSubType")
    @Expose
    private String mTextBoxItemSubType;

    @SerializedName("ImageWidth")
    @Expose
    private int mImageWidth;

    @SerializedName("ImageHeight")
    @Expose
    private int mImageHeight;

    @SerializedName("NavigateUrl")
    @Expose
    private String mNavigateUrl;

    @SerializedName("RefreshIntervalSeconds")
    @Expose
    private int mRefreshIntervalSeconds;

    @SerializedName("ZipCode")
    @Expose
    private int mZipCode;

    @SerializedName("InnerHtml")
    @Expose
    private int mInnerHtml;

    public String getTemplateItemModelBaseItemType() {
        return mItemType;
    }

    public int getTemplateItemModelBaseDisplayOrder() {
        return mDisplayOrder;
    }

    public String getTemplateItemModelBaseLabel() {
        return mLabel;
    }

    public String getTemplateItemModelBaseItemId() {
        return mItemId;
    }

    public String getTemplateItemModelBaseItemFieldID() {
        return mItemFieldID;
    }

    public String getTemplateItemModelBasePlaceholder() {
        return mPlaceholder;
    }

    public String getTemplateItemModelBaseResourceUrl() {
        return mResourceUrl;
    }

    public String getTemplateItemModelBaseSelectedLabel() {
        return mSelectedLabel;
    }

    public String getTemplateItemModelBaseUnSelectedLabel() {
        return mUnSelectedLabel;
    }

    public List<FFTFormTemplateItemKeyValue> getTemplateItemModelBaseItems() {
        return Items;
    }

    public int getTemplateItemModelBaseColumnsCount() {
        return mColumnsCount;
    }

    public int getRowsCount() {
        return mRowsCount;
    }

    public String getTemplateItemModelBaseDateItemSubType() {
        return mDateItemSubType;
    }

    public String getTemplateItemModelBaseTextBoxItemSubType() {
        return mTextBoxItemSubType;
    }

    public int getTemplateItemModelBaseImageWidth() {
        return mImageWidth;
    }

    public int getTemplateItemModelBaseImageHeight() {
        return mImageHeight;
    }

    public String getTemplateItemModelBaseNavigateUrl() {
        return mNavigateUrl;
    }

    public int getTemplateItemModelBaseRefreshIntervalSeconds() {
        return mRefreshIntervalSeconds;
    }

    public int getTemplateItemModelBaseZipCode() {
        return mZipCode;
    }

    public int getTemplateItemModelBaseInnerHtml() {
        return mInnerHtml;
    }
}
