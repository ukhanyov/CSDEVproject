package com.example.admin_linux.csdevproject.data.models.favorites.tabs;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteFormTemplateItem implements Parcelable {

    private String mType;
    private String mLabel;
    private String mResourceUrl;
    private String mInnerHtml;

    public FavoriteFormTemplateItem(String type, String label, String resourceUrl, String innerHtml) {
        this.mType = type;
        this.mLabel = label;
        this.mResourceUrl = resourceUrl;
        this.mInnerHtml = innerHtml;
    }

    public String getFavoriteFormTemplateType() {
        return mType;
    }

    public String getFavoriteFormTemplateLabel() {
        return mLabel;
    }

    public String getFavoriteFormTemplateResourceUrl() {
        return mResourceUrl;
    }

    public String getFavoriteFormTemplateInnerHtml() {
        return mInnerHtml;
    }

    // Parcelling part
    public FavoriteFormTemplateItem(Parcel in) {
        String[] dataString = new String[4];
        in.readStringArray(dataString);

        this.mType = dataString[0];
        this.mLabel = dataString[1];
        this.mResourceUrl = dataString[2];
        this.mInnerHtml = dataString[3];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.mType,
                this.mLabel,
                this.mResourceUrl,
                this.mInnerHtml
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        public FavoriteFormTemplateItem createFromParcel(Parcel in) { return new FavoriteFormTemplateItem(in); }

        public FavoriteFormTemplateItem[] newArray(int size) {return new FavoriteFormTemplateItem[size]; }
    };
}
