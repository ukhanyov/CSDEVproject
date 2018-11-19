package com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel;

import com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.FavoriteEntryGroupModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteEntriesModel {

    @SerializedName("Groups")
    @Expose
    private List<FavoriteEntryGroupModel> mGroups;

    public List<FavoriteEntryGroupModel> getFavoriteEntriesModelGroups() {
        return mGroups;
    }
}
