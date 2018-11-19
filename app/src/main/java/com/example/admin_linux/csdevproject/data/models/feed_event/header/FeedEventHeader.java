package com.example.admin_linux.csdevproject.data.models.feed_event.header;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class FeedEventHeader implements Parcelable {

    private String mBigImageUrl;
    private String mSmallImageUrlOne;
    private String mSmallImageUrlTwo;
    private String mFirstNameString;
    private String mSecondNameString;
    private String mFeedEventHeaderDate;
    private List<InvolvedPeople> mInvolvedPeople;
    private boolean mFirstMessage;
    private boolean mAChat;

    public FeedEventHeader(String bigImageUrl,
                           String smallImageUrlOne,
                           String smallImageUrlTwo,
                           String firstNameString,
                           String secondNameString,
                           String date,
                           List<InvolvedPeople> people,
                           boolean firstMessage,
                           boolean aChat) {
        this.mBigImageUrl = bigImageUrl;
        this.mSmallImageUrlOne = smallImageUrlOne;
        this.mSmallImageUrlTwo = smallImageUrlTwo;
        this.mFirstNameString = firstNameString;
        this.mSecondNameString = secondNameString;
        this.mFeedEventHeaderDate = date;
        this.mInvolvedPeople = people;
        this.mFirstMessage = firstMessage;
        this.mAChat = aChat;
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

    public List<InvolvedPeople> getInvolvedPeople() {
        return mInvolvedPeople;
    }

    public boolean isFirstMessage() {
        return mFirstMessage;
    }

    public boolean isAChat() {
        return mAChat;
    }

    public FeedEventHeader(Parcel in){
        boolean[] dataBoolean = new boolean[2];
        in.readBooleanArray(dataBoolean);

        this.mBigImageUrl = in.readString();
        this.mSmallImageUrlOne = in.readString();
        this.mSmallImageUrlTwo = in.readString();
        this.mFirstNameString = in.readString();
        this.mSecondNameString = in.readString();
        this.mFeedEventHeaderDate = in.readString();
        this.mFirstMessage = dataBoolean[0];
        this.mAChat = dataBoolean[1];

        List<InvolvedPeople> list = new ArrayList<>();
        in.readTypedList(list, InvolvedPeople.CREATOR);
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
        dest.writeTypedList(this.mInvolvedPeople);
        dest.writeBooleanArray(new boolean[] {
                this.mFirstMessage,
                this.mAChat
        });
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
