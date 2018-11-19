package com.example.admin_linux.csdevproject.data.models.feed_event;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedEventHeader implements Parcelable {
    private String mBigImageUrl;
    private String mSmallImageUrlOne;
    private String mSmallImageUrlTwo;
    private String mFirstNameString;
    private String mSecondNameString;
    private String mFeedEventHeaderDate;

    public FeedEventHeader(String bigImageUrl, String smallImageUrlOne, String smallImageUrlTwo, String firstNameString, String secondNameString, String date) {
        this.mBigImageUrl = bigImageUrl;
        this.mSmallImageUrlOne = smallImageUrlOne;
        this.mSmallImageUrlTwo = smallImageUrlTwo;
        this.mFirstNameString = firstNameString;
        this.mSecondNameString = secondNameString;
        this.mFeedEventHeaderDate = date;
    }

    public String getBigImageUrl() {
        return mBigImageUrl;
    }

    public String getSmallImageUrlOne() {
        return mSmallImageUrlOne;
    }

    public String getSmallImageUrlTwo() {
        return mSmallImageUrlTwo;
    }

    public String getFirstNameString() {
        return mFirstNameString;
    }

    public String getSecondNameString() {
        return mSecondNameString;
    }

    public String getFeedEventHeaderDate() {
        return mFeedEventHeaderDate;
    }

    public FeedEventHeader(Parcel in){
        this.mBigImageUrl = in.readString();
        this.mSmallImageUrlOne = in.readString();
        this.mSmallImageUrlTwo = in.readString();
        this.mFirstNameString = in.readString();
        this.mSecondNameString = in.readString();
        this.mFeedEventHeaderDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mBigImageUrl);
        dest.writeString(this.mSmallImageUrlOne);
        dest.writeString(this.mSmallImageUrlTwo);
        dest.writeString(this.mFirstNameString);
        dest.writeString(this.mSecondNameString);
        dest.writeString(this.mFeedEventHeaderDate);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FeedEventHeader createFromParcel(Parcel in) {
            return new FeedEventHeader(in);
        }

        public FeedEventHeader[] newArray(int size) {
            return new FeedEventHeader[size];
        }
    };
}
