package com.example.admin_linux.csdevproject.data;

public class FeedEvent {
    private String mImageUrl;
    private String mName;
    private String mCorpName;
    private String mDate;
    private String mRepliedTo;
    private String mReplyDestination;

    public FeedEvent(String mImageUrl, String mName, String mCorpName, String mDate, String mRepliedTo, String mReplyDestination) {
        this.mImageUrl = mImageUrl;
        this.mName = mName;
        this.mCorpName = mCorpName;
        this.mDate = mDate;
        this.mRepliedTo = mRepliedTo;
        this.mReplyDestination = mReplyDestination;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCorpName() {
        return mCorpName;
    }

    public void setmCorpName(String mCorpName) {
        this.mCorpName = mCorpName;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmRepliedTo() {
        return mRepliedTo;
    }

    public void setmRepliedTo(String mRepliedTo) {
        this.mRepliedTo = mRepliedTo;
    }

    public String getmReplyDestination() {
        return mReplyDestination;
    }

    public void setmReplyDestination(String mReplyDestination) {
        this.mReplyDestination = mReplyDestination;
    }
}
