package com.example.admin_linux.csdevproject.data.models.feed_event.body;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedEventBodyCardMessage implements Parcelable {

    private int mCardId;
    private String mMessage;
    private double mAspectRatio;
    private String mMessageType;
    private boolean mProcessLinks;

    public FeedEventBodyCardMessage(int cardId,
                                    String message,
                                    double aspectRatio,
                                    String messageType,
                                    boolean processLinks) {

        this.mCardId = cardId;
        this.mMessage = message;
        this.mAspectRatio = aspectRatio;
        this.mMessageType = messageType;
        this.mProcessLinks = processLinks;
    }

    public int getBodyCardMessageId() {
        return mCardId;
    }

    public String getBodyCardMessageMessage() {
        return mMessage;
    }

    public double getBodyCardMessageAspectRatio() {
        return mAspectRatio;
    }

    public String getBodyCardMessageMessageType() {
        return mMessageType;
    }

    public boolean isBodyCardMessageProcessLinks() {
        return mProcessLinks;
    }

    public FeedEventBodyCardMessage(Parcel in){
        boolean[] dataBoolean = new boolean[1];
        in.readBooleanArray(dataBoolean);

        this.mCardId = in.readInt();
        this.mMessage = in.readString();
        this.mAspectRatio = in.readDouble();
        this.mMessageType = in.readString();
        this.mProcessLinks = dataBoolean[0];
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mCardId);
        dest.writeString(this.mMessage);
        dest.writeDouble(this.mAspectRatio);
        dest.writeString(this.mMessageType);
        dest.writeBooleanArray(new boolean[] {
                this.mProcessLinks
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FeedEventBodyCardMessage createFromParcel(Parcel in) {
            return new FeedEventBodyCardMessage(in);
        }

        public FeedEventBodyCardMessage[] newArray(int size) {
            return new FeedEventBodyCardMessage[size];
        }
    };
}
