package com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.event_item_sub_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedEventItemModelFeedEventTypeTypes {

    @SerializedName("ConversationMessage")
    @Expose
    private int conversationMessage;

    @SerializedName("ActivityCreated")
    @Expose
    private int activityCreated;

    @SerializedName("ActivityStatusChange")
    @Expose
    private int activityStatusChange;

    @SerializedName("ActivityItemChange")
    @Expose
    private int activityItemChange;

    @SerializedName("ActivityMultipleChanges")
    @Expose
    private int activityMultipleChanges;

    @SerializedName("ActivityItemDeleted")
    @Expose
    private int activityItemDeleted;

    @SerializedName("ActivityItemAdded")
    @Expose
    private int activityItemAdded;

    @SerializedName("ActivityDeleted")
    @Expose
    private int activityDeleted;

    @SerializedName("ActivityAssignment")
    @Expose
    private int activityAssignment;

    @SerializedName("ActivityAssignmentAndStatus")
    @Expose
    private int activityAssignmentAndStatus;

    @SerializedName("ConversationPhoto")
    @Expose
    private int conversationPhoto;

    @SerializedName("ActivityPhoto")
    @Expose
    private int activityPhoto;

    @SerializedName("ActivityPhotoNote")
    @Expose
    private int activityPhotoNote;

    @SerializedName("PhotoDeleted")
    @Expose
    private int photoDeleted;

    @SerializedName("FavoriteProduct")
    @Expose
    private int favoriteProduct;

    @SerializedName("CatalogEntryPosted")
    @Expose
    private int catalogEntryPosted;

    @SerializedName("CardTemplatedMessagePosted")
    @Expose
    private int cardTemplatedMessagePosted;

    @SerializedName("ActivityCardPosted")
    @Expose
    private int activityCardPosted;

    @SerializedName("CatalogEntryPostedLive")
    @Expose
    private int catalogEntryPostedLive;

    public int getConversationMessage() {
        return conversationMessage;
    }

    public int getActivityCreated() {
        return activityCreated;
    }

    public int getActivityStatusChange() {
        return activityStatusChange;
    }

    public int getActivityItemChange() {
        return activityItemChange;
    }

    public int getActivityMultipleChanges() {
        return activityMultipleChanges;
    }

    public int getActivityItemDeleted() {
        return activityItemDeleted;
    }

    public int getActivityItemAdded() {
        return activityItemAdded;
    }

    public int getActivityDeleted() {
        return activityDeleted;
    }

    public int getActivityAssignment() {
        return activityAssignment;
    }

    public int getActivityAssignmentAndStatus() {
        return activityAssignmentAndStatus;
    }

    public int getConversationPhoto() {
        return conversationPhoto;
    }

    public int getActivityPhoto() {
        return activityPhoto;
    }

    public int getActivityPhotoNote() {
        return activityPhotoNote;
    }

    public int getPhotoDeleted() {
        return photoDeleted;
    }

    public int getFavoriteProduct() {
        return favoriteProduct;
    }

    public int getCatalogEntryPosted() {
        return catalogEntryPosted;
    }

    public int getCardTemplatedMessagePosted() {
        return cardTemplatedMessagePosted;
    }

    public int getActivityCardPosted() {
        return activityCardPosted;
    }

    public int getCatalogEntryPostedLive() {
        return catalogEntryPostedLive;
    }
}
