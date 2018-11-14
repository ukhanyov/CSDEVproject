package com.example.admin_linux.csdevproject.network.pojo.register_device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RDResponse {

    @SerializedName("ResultCode")
    @Expose
    private int mResultCode;

    @SerializedName("ResultCodeName")
    @Expose
    private String mResultCodeName;

    @SerializedName("ErrorMessage")
    @Expose
    private String mErrorMessage;

    @SerializedName("WarningMessage")
    @Expose
    private String mWarningMessage;

    @SerializedName("ServerTimeMinutes")
    @Expose
    private int mServerTimeMinutes;

    public int getResultCode() {
        return mResultCode;
    }

    public String getResultCodeName() {
        return mResultCodeName;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public String getWarningMessage() {
        return mWarningMessage;
    }

    public int getServerTimeMinutes() {
        return mServerTimeMinutes;
    }
}
