package com.example.admin_linux.csdevproject.data.models.favorites.tabs;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteFormTemplate implements Parcelable {

    private String mType;
    private String mLabel;
    private String mResourceUrl;
    private String mInnerHtml;

    public FavoriteFormTemplate(String type, String label, String resourceUrl, String innerHtml) {
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
    public FavoriteFormTemplate(Parcel in) {
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

        public FavoriteFormTemplate createFromParcel(Parcel in) { return new FavoriteFormTemplate(in); }

        public FavoriteFormTemplate[] newArray(int size) {return new FavoriteFormTemplate[size]; }
    };
}
