package com.example.admin_linux.csdevproject.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedEventItemModelFeedEventPersonModel {

    @SerializedName("PersonId")
    @Expose
    private int personId;

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

    public int getPersonId() {
        return personId;
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getIconPath() {
        return iconPath;
    }
}
