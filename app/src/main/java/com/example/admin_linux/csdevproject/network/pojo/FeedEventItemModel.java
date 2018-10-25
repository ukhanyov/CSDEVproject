package com.example.admin_linux.csdevproject.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedEventItemModel {

    @SerializedName("Person")
    @Expose
    private FeedEventItemModelPerson person;

    @SerializedName("Organization")
    @Expose
    private FeedEventItemModelOrganization organization;

    @SerializedName("FeedEventId")
    @Expose
    private int feedEventId;

    @SerializedName("ActivityId")
    @Expose
    private int activityId;

    @SerializedName("ConversationId")
    @Expose
    private int conversationId;

    @SerializedName("FeedEventTitle")
    @Expose
    private String feedEventTitle;

    @SerializedName("DisplayText")
    @Expose
    private String displayText;

    @SerializedName("FeedImage")
    @Expose
    private FeedEventItemModelFeedImage feedImage;

    @SerializedName("ItemId")
    @Expose
    private int itemId;

    @SerializedName("CatalogEntryId")
    @Expose
    private int catalogEntryId;

    @SerializedName("CatalogEntryItemTemplateId")
    @Expose
    private int catalogEntryItemTemplateId;

    // TODO: scout about SDateTimeOffset
//    @SerializedName("OnDate")
//    @Expose
//    private FeedEventItemModelSDateTimeOffset onDate;

    @SerializedName("FeedType")
    @Expose
    private FeedEventItemModelFeedEventTypeTypes feedType;

    @SerializedName("ActivityCropShortInfo")
    @Expose
    private FeedEventItemModelActivityCropShortInfoModel activityCropShortInfo;

    @SerializedName("InvolvedPersons")
    @Expose
    private List<FeedEventItemModelFeedEventPersonModel> involvedPersons;

    @SerializedName("Comments")
    @Expose
    private String comments;

    @SerializedName("DefaultConversationUserId")
    @Expose
    private int defaultConversationUserId;

    @SerializedName("CardRenderDataId")
    @Expose
    private int cardRenderDataId;

    @SerializedName("FeedSourceinFavorites")
    @Expose
    private boolean feedSourceinFavorites;

    @SerializedName("ConversationFirstMessage")
    @Expose
    private boolean conversationFirstMessage;

    public FeedEventItemModelPerson getPerson() {
        return person;
    }

    public FeedEventItemModelOrganization getOrganization() {
        return organization;
    }

    public int getFeedEventId() {
        return feedEventId;
    }

    public int getActivityId() {
        return activityId;
    }

    public int getConversationId() {
        return conversationId;
    }

    public String getFeedEventTitle() {
        return feedEventTitle;
    }

    public String getDisplayText() {
        return displayText;
    }

    public FeedEventItemModelFeedImage getFeedImage() {
        return feedImage;
    }

    public int getItemId() {
        return itemId;
    }

    public int getCatalogEntryId() {
        return catalogEntryId;
    }

    public int getCatalogEntryItemTemplateId() {
        return catalogEntryItemTemplateId;
    }

//    public FeedEventItemModelSDateTimeOffset getOnDate() {
//        return onDate;
//    }

    public FeedEventItemModelFeedEventTypeTypes getFeedType() {
        return feedType;
    }

    public FeedEventItemModelActivityCropShortInfoModel getActivityCropShortInfo() {
        return activityCropShortInfo;
    }

    public List<FeedEventItemModelFeedEventPersonModel> getInvolvedPersons() {
        return involvedPersons;
    }

    public String getComments() {
        return comments;
    }

    public int getDefaultConversationUserId() {
        return defaultConversationUserId;
    }

    public int getCardRenderDataId() {
        return cardRenderDataId;
    }

    public boolean isFeedSourceinFavorites() {
        return feedSourceinFavorites;
    }

    public boolean isConversationFirstMessage() {
        return conversationFirstMessage;
    }
}
