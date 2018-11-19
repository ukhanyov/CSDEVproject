package com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.favorite_possible_item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoritePossibleItem {

    @SerializedName("FavoriteCatalogEntryId")
    @Expose
    private int mFavoriteCatalogEntryId;

    @SerializedName("CatalogEntryId")
    @Expose
    private int mCatalogEntryId;

    @SerializedName("EntryDisplayOrder")
    @Expose
    private int mEntryDisplayOrder;

    @SerializedName("ItemType")
    @Expose
    private String mItemType;

    @SerializedName("ItemTypeId")
    @Expose
    private int mItemTypeId;

    @SerializedName("ItemTemplateId")
    @Expose
    private int mItemTemplateId;

    @SerializedName("ConversationUserId")
    @Expose
    private int mConversationUserId;

    @SerializedName("Name")
    @Expose
    private String mName;

    @SerializedName("Manufacturer")
    @Expose
    private String mManufacturer;

    @SerializedName("IsFavorite")
    @Expose
    private boolean mIsFavorite;

    public int getFavoriteCatalogEntryId() {
        return mFavoriteCatalogEntryId;
    }

    public int getFavoritePossibleItemCatalogEntryId() {
        return mCatalogEntryId;
    }

    public int getFavoritePossibleItemEntryDisplayOrder() {
        return mEntryDisplayOrder;
    }

    public String getFavoritePossibleItemItemType() {
        return mItemType;
    }

    public int getFavoritePossibleItemItemTypeId() {
        return mItemTypeId;
    }

    public int getFavoritePossibleItemItemTemplateId() {
        return mItemTemplateId;
    }

    public int getFavoritePossibleItemConversationUserId() {
        return mConversationUserId;
    }

    public String getFavoritePossibleItemName() {
        return mName;
    }

    public String getFavoritePossibleItemManufacturer() {
        return mManufacturer;
    }

    public boolean isFavoritePossibleItemFavorite() {
        return mIsFavorite;
    }
}
