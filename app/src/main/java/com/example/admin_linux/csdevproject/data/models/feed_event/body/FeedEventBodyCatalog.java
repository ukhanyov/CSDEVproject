package com.example.admin_linux.csdevproject.data.models.feed_event.body;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedEventBodyCatalog implements Parcelable {

    private int mCatalogId;

    public FeedEventBodyCatalog(int catalogId) {
        this.mCatalogId = catalogId;
    }

    public int getmCatalogId() {
        return mCatalogId;
    }

    public FeedEventBodyCatalog(Parcel in){
        this.mCatalogId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mCatalogId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FeedEventBodyCatalog createFromParcel(Parcel in) {
            return new FeedEventBodyCatalog(in);
        }

        public FeedEventBodyCatalog[] newArray(int size) {
            return new FeedEventBodyCatalog[size];
        }
    };
}
