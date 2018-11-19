package com.example.admin_linux.csdevproject.network.pojo.favorite_entries;

import com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.FavoriteEntriesModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoriteEntriesReturnValue {

    @SerializedName("ReturnValue")
    @Expose
    private FavoriteEntriesModel mReturnValue;

    public FavoriteEntriesModel getReturnValue() {
        return mReturnValue;
    }
}
