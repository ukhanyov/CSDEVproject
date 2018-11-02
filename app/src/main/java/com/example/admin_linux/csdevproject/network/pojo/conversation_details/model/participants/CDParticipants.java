package com.example.admin_linux.csdevproject.network.pojo.conversation_details.model.participants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CDParticipants {
    /*
        "PersonId": 1,
        "Name": "sample string 2",
        "FirstName": "sample string 3",
        "LastName": "sample string 4",
        "ProfileImageUrl": "sample string 5",
        "UserId": 1,
        "OrganizationName": "sample string 6",
        "City": "sample string 7",
        "State": "sample string 8",
        "Country": "sample string 9",
        "PersonDisplayName": "sample string 3",
        "PersonFirstName": "sample string 3",
        "PersonLastName": "sample string 4",
        "PersonFullName": "sample string 2",
        "PersonImageUrl": "sample string 5"
     */

    @SerializedName("UserId")
    @Expose
    private int mUserId;

    @SerializedName("PersonFirstName")
    @Expose
    private String mPersonFirstName;

    @SerializedName("PersonFullName")
    @Expose
    private String mPersonFullName;

    @SerializedName("PersonImageUrl")
    @Expose
    private String mPersonImageUrl;

    public int getUserId() {
        return mUserId;
    }

    public String getPersonFullName() {
        return mPersonFullName;
    }

    public String getPersonImageUrl() {
        return mPersonImageUrl;
    }

    public String getPersonFirstName() {
        return mPersonFirstName;
    }
}
