package com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.event_item_sub_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FEIMPerson {

    @SerializedName("PersonId")
    @Expose
    private int personIs;

    @SerializedName("PersonFirstName")
    @Expose
    private String personFirstName;

    @SerializedName("PersonLastName")
    @Expose
    private String personLastName;

    @SerializedName("PersonFullName")
    @Expose
    private String personFullName;

    @SerializedName("IsFavorite")
    @Expose
    private boolean isFavorite;

    @SerializedName("OrganizationName")
    @Expose
    private String organizationName;

    @SerializedName("IconPath")
    @Expose
    private String iconPath;

    public int getPersonIs() {
        return personIs;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public String getPersonFullName() {
        return personFullName;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getIconPath() {
        return iconPath;
    }
}
