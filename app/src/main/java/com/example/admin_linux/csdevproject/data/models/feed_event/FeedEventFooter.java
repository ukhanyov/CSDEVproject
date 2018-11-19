package com.example.admin_linux.csdevproject.data.models.feed_event;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedEventFooter implements Parcelable {

    private boolean mStartPrivateChat;

    public FeedEventFooter(boolean startPrivateChat) {
        this.mStartPrivateChat = startPrivateChat;
    }

    public boolean isStartPrivateChat() {
        return mStartPrivateChat;
    }

    public FeedEventFooter(Parcel in){

        boolean[] data = new boolean[1];
        in.readBooleanArray(data);
        this.mStartPrivateChat = data[0];
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBooleanArray(new boolean[] {
                this.mStartPrivateChat
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FeedEventFooter createFromParcel(Parcel in) {
            return new FeedEventFooter(in);
        }

        public FeedEventFooter[] newArray(int size) {
            return new FeedEventFooter[size];
        }
    };
}
