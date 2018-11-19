package com.example.admin_linux.csdevproject.data.models.feed_event.body;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedEventBodyCard implements Parcelable {

    private int mCardId;

    public FeedEventBodyCard(int cardId) {
        this.mCardId = cardId;
    }

    public int getCardId() {
        return mCardId;
    }

    public FeedEventBodyCard(Parcel in){
        this.mCardId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mCardId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FeedEventBodyCard createFromParcel(Parcel in) {
            return new FeedEventBodyCard(in);
        }

        public FeedEventBodyCard[] newArray(int size) {
            return new FeedEventBodyCard[size];
        }
    };
}
