package com.example.admin_linux.csdevproject.data.models.feed_event;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.admin_linux.csdevproject.data.models.feed_event.body.FeedEventBody;
import com.example.admin_linux.csdevproject.data.models.feed_event.footer.FeedEventFooter;
import com.example.admin_linux.csdevproject.data.models.feed_event.header.FeedEventHeader;

public class FeedEvent implements Parcelable {
    private FeedEventHeader mFeedEventHeader;
    private FeedEventBody mFeedEventBody;
    private FeedEventFooter mFeedEventFooter;

    public FeedEvent(FeedEventHeader feedEventHeader, FeedEventBody feedEventBody, FeedEventFooter feedEventFooter) {
        this.mFeedEventHeader = feedEventHeader;
        this.mFeedEventBody = feedEventBody;
        this.mFeedEventFooter = feedEventFooter;
    }

    public FeedEventHeader getmFeedEventHeader() {
        return mFeedEventHeader;
    }

    public FeedEventBody getFeedEventBody() {
        return mFeedEventBody;
    }

    public FeedEventFooter getFeedEventFooter() {
        return mFeedEventFooter;
    }

    // Parcelling part
    public FeedEvent(Parcel in) {
        this.mFeedEventHeader = in.readParcelable(FeedEventHeader.class.getClassLoader());
        this.mFeedEventBody = in.readParcelable(FeedEventBody.class.getClassLoader());
        this.mFeedEventFooter = in.readParcelable(FeedEventFooter.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mFeedEventHeader, flags);
        dest.writeParcelable(this.mFeedEventBody, flags);
        dest.writeParcelable(this.mFeedEventFooter, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FeedEvent createFromParcel(Parcel in) {
            return new FeedEvent(in);
        }

        public FeedEvent[] newArray(int size) {
            return new FeedEvent[size];
        }
    };
}
