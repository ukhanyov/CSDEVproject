package com.example.admin_linux.csdevproject.data.models.favorites;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.admin_linux.csdevproject.data.models.favorites.tabs.FavoritesTabs;

import java.util.ArrayList;
import java.util.List;

public class Favorites implements Parcelable {

    private List<FavoritesTabs> mFavoritesTabs;

    public Favorites(List<FavoritesTabs> favoritesTabs) {
        this.mFavoritesTabs = favoritesTabs;
    }

    public List<FavoritesTabs> getFavoritesTabs() {
        return mFavoritesTabs;
    }

    // Parcelling part
    public Favorites(Parcel in) {
        List<FavoritesTabs> listFavoritesTabs = new ArrayList<>();
        in.readTypedList(listFavoritesTabs, FavoritesTabs.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mFavoritesTabs);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Favorites createFromParcel(Parcel in) {
            return new Favorites(in);
        }

        public Favorites[] newArray(int size) {
            return new Favorites[size];
        }
    };

}
