package com.example.admin_linux.csdevproject.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedEventItemModelPerson {

    @SerializedName("PersonId")
    @Expose
    private int personIs;

    @SerializedName("PersonFirstName")
    @Expose
    private int personFirstName;

    @SerializedName("PersonLastName")
    @Expose
    private int personLastName;

    @SerializedName("PersonFullName")
    @Expose
    private int personFullName;

    @SerializedName("IsFavorite")
    @Expose
    private int isFavorite;

    @SerializedName("OrganizationName")
    @Expose
    private int organizationName;

    @SerializedName("IconPath")
    @Expose
    private int iconPath;

    public int getPersonIs() {
        return personIs;
    }

    public int getPersonFirstName() {
        return personFirstName;
    }

    public int getPersonLastName() {
        return personLastName;
    }

    public int getPersonFullName() {
        return personFullName;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public int getOrganizationName() {
        return organizationName;
    }

    public int getIconPath() {
        return iconPath;
    }
}
