package com.example.admin_linux.csdevproject.data.models.feed_event.body;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedEventBody implements Parcelable {
    private String mFeedEventBodyMessage;
    private FeedEventBodyCardMessage mFeedEventBodyCardMessage;
    private FeedEventBodyCatalog mFeedEventBodyCatalog;

    public FeedEventBody(String feedEventBodyMessage, FeedEventBodyCardMessage feedEventBodyCardMessage, FeedEventBodyCatalog feedEventBodyCatalog) {
        this.mFeedEventBodyMessage = feedEventBodyMessage;
        this.mFeedEventBodyCardMessage = feedEventBodyCardMessage;
        this.mFeedEventBodyCatalog = feedEventBodyCatalog;
    }

    public String getFeedEventBodyMessage() {
        return mFeedEventBodyMessage;
    }

    public FeedEventBodyCardMessage getFeedEventBodyCard() {
        return mFeedEventBodyCardMessage;
    }

    public FeedEventBodyCatalog getFeedEventBodyCatalog() {
        return mFeedEventBodyCatalog;
    }

    public FeedEventBody(Parcel in){
        this.mFeedEventBodyMessage = in.readString();
        this.mFeedEventBodyCardMessage = in.readParcelable(FeedEventBodyCardMessage.class.getClassLoader());
        this.mFeedEventBodyCatalog = in.readParcelable(FeedEventBodyCatalog.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mFeedEventBodyMessage);
        dest.writeParcelable(this.mFeedEventBodyCardMessage, flags);
        dest.writeParcelable(this.mFeedEventBodyCatalog, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FeedEventBody createFromParcel(Parcel in) {
            return new FeedEventBody(in);
        }

        public FeedEventBody[] newArray(int size) {
            return new FeedEventBody[size];
        }
    };
}
