package com.example.admin_linux.csdevproject.data.models.favorites.tabs;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoritePossibleItem implements Parcelable {

    private String mItemType;
    private String mName;
    private String mManufacturer;

    public FavoritePossibleItem(String itemType, String name, String manufacturer) {
        this.mItemType = itemType;
        this.mName = name;
        this.mManufacturer = manufacturer;
    }

    public String getFavoriteFormTemplateItemType() {
        return mItemType;
    }

    public String getFavoriteFormTemplateName() {
        return mName;
    }

    public String getFavoriteFormTemplateManufacturer() {
        return mManufacturer;
    }

    // Parcelling part
    public FavoritePossibleItem(Parcel in) {
        String[] dataString = new String[3];
        in.readStringArray(dataString);

        this.mItemType = dataString[0];
        this.mName = dataString[1];
        this.mManufacturer = dataString[2];
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.mItemType,
                this.mName,
                this.mManufacturer
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FavoritePossibleItem createFromParcel(Parcel in) {
            return new FavoritePossibleItem(in);
        }

        public FavoritePossibleItem[] newArray(int size) {
            return new FavoritePossibleItem[size];
        }
    };
}
