package com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item;

import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.event_item_sub_models.FEIMInvolvedPerson;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.event_item_sub_models.FEIMFeedImage;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.event_item_sub_models.FEIMlOrganization;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.event_item_sub_models.FEIMPerson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedEventItemModel {

    @SerializedName("Person")
    @Expose
    private FEIMPerson person;

    @SerializedName("Organization")
    @Expose
    private FEIMlOrganization organization;

//    @SerializedName("FeedEventId")
//    @Expose
//    private int feedEventId;

//    @SerializedName("ActivityId")
//    @Expose
//    private int activityId;

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
    private FEIMFeedImage feedImage;

//    @SerializedName("ItemId")
//    @Expose
//    private int itemId;

//    @SerializedName("CatalogEntryId")
//    @Expose
//    private int catalogEntryId;

//    @SerializedName("CatalogEntryItemTemplateId")
//    @Expose
//    private int catalogEntryItemTemplateId;

    @SerializedName("OnDate")
    @Expose
    private String onDate;

    @SerializedName("FeedType")
    @Expose
    private String feedType;

//    @SerializedName("ActivityCropShortInfo")
//    @Expose
//    private FEIMActivityCropShortInfoModel activityCropShortInfo;

    @SerializedName("InvolvedPersons")
    @Expose
    private List<FEIMInvolvedPerson> involvedPersons;

    @SerializedName("Comments")
    @Expose
    private String comments;

//    @SerializedName("DefaultConversationUserId")
//    @Expose
//    private int defaultConversationUserId;

    @SerializedName("CardRenderDataId")
    @Expose
    private int mCardRenderDataId;

//    @SerializedName("FeedSourceinFavorites")
//    @Expose
//    private boolean feedSourceinFavorites;

    @SerializedName("ConversationFirstMessage")
    @Expose
    private boolean conversationFirstMessage;

    public FEIMPerson getPerson() {
        return person;
    }

    public FEIMlOrganization getOrganization() {
        return organization;
    }

//    public int getFeedEventId() {
//        return feedEventId;
//    }
//
//    public int getActivityId() {
//        return activityId;
//    }

    public int getConversationId() {
        return conversationId;
    }

    public String getFeedEventTitle() {
        return feedEventTitle;
    }

    public String getDisplayText() {
        return displayText;
    }

    public FEIMFeedImage getFeedImage() {
        return feedImage;
    }

//    public int getItemId() {
//        return itemId;
//    }
//
//    public int getCatalogEntryId() {
//        return catalogEntryId;
//    }
//
//    public int getCatalogEntryItemTemplateId() {
//        return catalogEntryItemTemplateId;
//    }

    public String getOnDate() {
        return onDate;
    }

    public String getFeedType() {
        return feedType;
    }
//
//    public FEIMActivityCropShortInfoModel getActivityCropShortInfo() {
//        return activityCropShortInfo;
//    }

    public List<FEIMInvolvedPerson> getInvolvedPersons() {
        return involvedPersons;
    }

    public String getComments() {
        return comments;
    }

//    public int getDefaultConversationUserId() {
//        return defaultConversationUserId;
//    }
//
    public int getCardRenderDataId() {
        return mCardRenderDataId;
    }
//
//    public boolean isFeedSourceinFavorites() {
//        return feedSourceinFavorites;
//    }

    public boolean isConversationFirstMessage() {
        return conversationFirstMessage;
    }

    public String returnInvolvedPersons(List<FEIMInvolvedPerson> list){
        StringBuilder sb = new StringBuilder();
        for(FEIMInvolvedPerson person : list){
            sb.append(person.getPersonFullName());
            sb.append(", ");
        }
        return null;
    }
}
