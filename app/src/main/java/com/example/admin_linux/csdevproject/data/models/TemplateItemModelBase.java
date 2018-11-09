package com.example.admin_linux.csdevproject.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TemplateItemModelBase implements Parcelable {

    private String mType;
    private String mLabel;
    private String mResourceUrl;
    private String mInnerHtml;

    public TemplateItemModelBase(String type, String label, String resourceUrl, String innerHtml) {
        this.mType = type;
        this.mLabel = label;
        this.mResourceUrl = resourceUrl;
        this.mInnerHtml = innerHtml;
    }

    public String getType() {
        return mType;
    }

    public String getLabel() {
        return mLabel;
    }

    public String getResourceUrl() {
        return mResourceUrl;
    }

    public String getInnerHtml() {
        return mInnerHtml;
    }

    // Parcelling part
    public TemplateItemModelBase(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);

        this.mType = data[0];
        this.mLabel = data[1];
        this.mResourceUrl = data[2];
        this.mInnerHtml = data[3];
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

        public TemplateItemModelBase createFromParcel(Parcel in) { return new TemplateItemModelBase(in); }

        public TemplateItemModelBase[] newArray(int size) {return new TemplateItemModelBase[size]; }
    };

}
