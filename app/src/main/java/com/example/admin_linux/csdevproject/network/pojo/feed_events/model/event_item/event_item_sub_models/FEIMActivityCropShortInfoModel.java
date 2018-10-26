package com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.event_item_sub_models;

import com.example.admin_linux.csdevproject.network.pojo.CoordinatesModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FEIMActivityCropShortInfoModel {

    @SerializedName("CropId")
    @Expose
    private int cropId;

    @SerializedName("FieldId")
    @Expose
    private int fieldId;

    @SerializedName("FieldName")
    @Expose
    private String fieldName;

    @SerializedName("FieldArea")
    @Expose
    private Double fieldArea;

    @SerializedName("CropName")
    @Expose
    private String cropName;

    @SerializedName("OperationName")
    @Expose
    private String operationName;

    @SerializedName("GpsCentroid")
    @Expose
    private CoordinatesModel gpsCentroid;

    @SerializedName("GpsEntryLocation")
    @Expose
    private CoordinatesModel gpsEntryLocation;

    public int getCropId() {
        return cropId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Double getFieldArea() {
        return fieldArea;
    }

    public String getCropName() {
        return cropName;
    }

    public String getOperationName() {
        return operationName;
    }

    public CoordinatesModel getGpsCentroid() {
        return gpsCentroid;
    }

    public CoordinatesModel getGpsEntryLocation() {
        return gpsEntryLocation;
    }
}
