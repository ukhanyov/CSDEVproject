package com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.favorite_form_template.template_model.model_base.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FFTFormTemplateItemKeyValue {

    @SerializedName("Key")
    @Expose
    private String mKey;

    @SerializedName("Value")
    @Expose
    private String mValue;

    public String getTemplateItemKeyValueKey() {
        return mKey;
    }

    public String getTemplateItemKeyValueValue() {
        return mValue;
    }
}
