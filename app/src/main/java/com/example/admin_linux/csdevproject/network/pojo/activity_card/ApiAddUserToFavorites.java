package com.example.admin_linux.csdevproject.network.pojo.activity_card;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiAddUserToFavorites {

    @SerializedName("ResultCode")
    @Expose
    private int mResultCode;

    public int getApiAddUserToFavoritesResultCode() {
        return mResultCode;
    }
}
