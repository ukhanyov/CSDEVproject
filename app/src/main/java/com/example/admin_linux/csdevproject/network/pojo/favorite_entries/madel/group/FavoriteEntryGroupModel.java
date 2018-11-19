package com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group;

import com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.favorite_form_template.FavoriteFormTemplate;
import com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.favorite_possible_item.FavoritePossibleItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteEntryGroupModel {

    @SerializedName("GroupId")
    @Expose
    private int mGroupId;

    @SerializedName("GroupName")
    @Expose
    private String mGroupName;

    @SerializedName("DisplayOrder")
    @Expose
    private int mDisplayOrder;

    @SerializedName("FormTemplatesList")
    @Expose
    private List<FavoriteFormTemplate> mFormTemplatesList;

    @SerializedName("PossibleItemsList")
    @Expose
    private List<FavoritePossibleItem> mPossibleItemsList;

    public int getGroupId() {
        return mGroupId;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public int getDisplayOrder() {
        return mDisplayOrder;
    }

    public List<FavoriteFormTemplate> getFormTemplatesList() {
        return mFormTemplatesList;
    }

    public List<FavoritePossibleItem> getPossibleItemsList() {
        return mPossibleItemsList;
    }
}
