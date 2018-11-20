package com.example.admin_linux.csdevproject.data.models.favorites.tabs;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class FavoritesTabs implements Parcelable {

    private String mTabName;
    private List<FavoriteFormTemplate> mFavoriteFormTemplateList;
    private List<FavoritePossibleItem> mFavoritePossibleItemList;

    public FavoritesTabs(String tabName,
                         List<FavoriteFormTemplate> favoriteFormTemplateList,
                         List<FavoritePossibleItem> favoritePossibleItemList) {

        this.mTabName = tabName;
        this.mFavoriteFormTemplateList = favoriteFormTemplateList;
        this.mFavoritePossibleItemList = favoritePossibleItemList;
    }

    public String getTabName() {
        return mTabName;
    }

    public List<FavoriteFormTemplate> getFavoriteFormTemplateList() {
        return mFavoriteFormTemplateList;
    }

    public List<FavoritePossibleItem> getFavoritePossibleItemList() {
        return mFavoritePossibleItemList;
    }

    // Parcelling part
    public FavoritesTabs(Parcel in) {

        in.readString();

        List<FavoriteFormTemplate> listFavoriteFormTemplate = new ArrayList<>();
        in.readTypedList(listFavoriteFormTemplate, FavoriteFormTemplate.CREATOR);

        List<FavoritePossibleItem> listFavoritePossibleItem = new ArrayList<>();
        in.readTypedList(listFavoritePossibleItem, FavoritePossibleItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTabName);
        dest.writeTypedList(this.mFavoriteFormTemplateList);
        dest.writeTypedList(this.mFavoritePossibleItemList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FavoritesTabs createFromParcel(Parcel in) {
            return new FavoritesTabs(in);
        }

        public FavoritesTabs[] newArray(int size) {
            return new FavoritesTabs[size];
        }
    };
}
