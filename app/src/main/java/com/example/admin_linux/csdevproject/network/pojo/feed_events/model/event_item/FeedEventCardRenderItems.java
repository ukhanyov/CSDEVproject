package com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item;

import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.card_render_models.CRMCardMessageModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedEventCardRenderItems {

    /*
    |x| CardRenderDataId
    CardType
    CatalogEntry
    |x| CardMessage
    Activity
    PossibleItem
    OriginalPostedPerson
    OriginalPostedOrganization
    OriginalPostedMessage
    OriginalPostedDate
     */

    @SerializedName("CardRenderDataId")
    @Expose
    private int mCardRenderDataId;

//    @SerializedName("CardType")
//    @Expose
//    private CardRenderTypes CardType;
//
    @SerializedName("CatalogEntry")
    @Expose
    private FeedEventCatalogEntries mCatalogEntry;

    @SerializedName("CardMessage")
    @Expose
    private CRMCardMessageModel mCardMessage;

//    @SerializedName("Activity")
//    @Expose
//    private ActivityCardRenderData Activity;
//
//    @SerializedName("PossibleItem")
//    @Expose
//    private PossibleItemModel PossibleItem;
//
//    @SerializedName("OriginalPostedPerson")
//    @Expose
//    private FeedEventPersonModel OriginalPostedPerson;
//
//    @SerializedName("OriginalPostedOrganization")
//    @Expose
//    private FeedEventOrganizationModel OriginalPostedOrganization;
//
//    @SerializedName("OriginalPostedMessage")
//    @Expose
//    private string OriginalPostedMessage;
//
//    @SerializedName("OriginalPostedDate")
//    @Expose
//    private SDateTimeOffset OriginalPostedDate;


    public int getCardRenderDataId() {
        return mCardRenderDataId;
    }

    public CRMCardMessageModel getCardMessage() {
        return mCardMessage;
    }

    public FeedEventCatalogEntries getCatalogEntry() {
        return mCatalogEntry;
    }
}
