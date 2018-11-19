package com.example.admin_linux.csdevproject.data.models.feed_event.body;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedEventBody implements Parcelable {
    private String mFeedEventBodyMessage;
    private FeedEventBodyCard mFeedEventBodyCard;
    private FeedEventBodyCatalog mFeedEventBodyCatalog;

    public FeedEventBody(String feedEventBodyMessage, FeedEventBodyCard feedEventBodyCard, FeedEventBodyCatalog feedEventBodyCatalog) {
        this.mFeedEventBodyMessage = feedEventBodyMessage;
        this.mFeedEventBodyCard = feedEventBodyCard;
        this.mFeedEventBodyCatalog = feedEventBodyCatalog;
    }

    public String getFeedEventBodyMessage() {
        return mFeedEventBodyMessage;
    }

    public FeedEventBodyCard getFeedEventBodyCard() {
        return mFeedEventBodyCard;
    }

    public FeedEventBodyCatalog getFeedEventBodyCatalog() {
        return mFeedEventBodyCatalog;
    }

    public FeedEventBody(Parcel in){
        this.mFeedEventBodyMessage = in.readString();
        this.mFeedEventBodyCard = in.readParcelable(FeedEventBodyCard.class.getClassLoader());
        this.mFeedEventBodyCatalog = in.readParcelable(FeedEventBodyCatalog.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mFeedEventBodyMessage);
        dest.writeParcelable(this.mFeedEventBodyCard, flags);
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
