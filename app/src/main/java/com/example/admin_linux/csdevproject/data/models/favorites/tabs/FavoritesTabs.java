package com.example.admin_linux.csdevproject.data.models.favorites.tabs;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class FavoritesTabs implements Parcelable {

    private List<FavoriteFormTemplate> mFavoriteFormTemplateList;
    private List<FavoritePossibleItem> mFavoritePossibleItemList;

    public FavoritesTabs(List<FavoriteFormTemplate> favoriteFormTemplateList, List<FavoritePossibleItem> favoritePossibleItemList) {
        this.mFavoriteFormTemplateList = favoriteFormTemplateList;
        this.mFavoritePossibleItemList = favoritePossibleItemList;
    }

    public List<FavoriteFormTemplate> getFavoriteFormTemplateList() {
        return mFavoriteFormTemplateList;
    }

    public List<FavoritePossibleItem> getFavoritePossibleItemList() {
        return mFavoritePossibleItemList;
    }

    // Parcelling part
    public FavoritesTabs(Parcel in) {
        List<FavoriteFormTemplate> listFavoriteFormTemplate = new ArrayList<>();
        in.readTypedList(listFavoriteFormTemplate, FavoriteFormTemplate.CREATOR);

        List<FavoritePossibleItem> listFavoritePossibleItem = new ArrayList<>();
        in.readTypedList(listFavoritePossibleItem, FavoritePossibleItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
