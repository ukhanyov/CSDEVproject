package com.example.admin_linux.csdevproject.network.pojo.firebase_user;

import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.FeedEventsModel;
import com.example.admin_linux.csdevproject.network.pojo.firebase_user.model.FireBaseUserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FirebaseUserReturnValue {

    @SerializedName("ReturnValue")
    @Expose
    private FireBaseUserModel mUserModel;

//    @SerializedName("ResultCode")
//    @Expose
//    private int mResultCode;
//
//    @SerializedName("ResultCodeName")
//    @Expose
//    private String mResultCodeName;
//
//    @SerializedName("ErrorMessage")
//    @Expose
//    private String mErrorMessage;
//
//    @SerializedName("WarningMessage")
//    @Expose
//    private String mWarningMessage;
//
//    @SerializedName("ServerTimeMinutes")
//    @Expose
//    private int mServerTimeMinutes;

    public FireBaseUserModel getFireBaseUserModel() {
        return mUserModel;
    }
}
