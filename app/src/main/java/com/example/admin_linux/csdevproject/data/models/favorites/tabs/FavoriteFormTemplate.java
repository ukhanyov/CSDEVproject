package com.example.admin_linux.csdevproject.data.models.favorites.tabs;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFormTemplate implements Parcelable{

    private List<FavoriteFormTemplateItem> mFavoriteFormTemplateItemList;

    public FavoriteFormTemplate(List<FavoriteFormTemplateItem> favoriteFormTemplateItemList) {
        this.mFavoriteFormTemplateItemList = favoriteFormTemplateItemList;
    }

    public List<FavoriteFormTemplateItem> getFavoriteFormTemplateItemList() {
        return mFavoriteFormTemplateItemList;
    }

    // Parcelling part
    public FavoriteFormTemplate(Parcel in) {
        List<FavoriteFormTemplateItem> listFavoriteFormTemplateItem = new ArrayList<>();
        in.readTypedList(listFavoriteFormTemplateItem, FavoriteFormTemplateItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mFavoriteFormTemplateItemList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FavoriteFormTemplate createFromParcel(Parcel in) {
            return new FavoriteFormTemplate(in);
        }

        public FavoriteFormTemplate[] newArray(int size) {
            return new FavoriteFormTemplate[size];
        }
    };
}
