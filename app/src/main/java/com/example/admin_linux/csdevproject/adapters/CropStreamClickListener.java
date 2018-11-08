package com.example.admin_linux.csdevproject.adapters;

import android.view.View;

import com.example.admin_linux.csdevproject.data.models.CropStreamMessage;

public interface CropStreamClickListener {
    void onClick(View view,
                 int conversationId,
                 int personId,
                 String profileName,
                 String personsCorp,
                 String personsPictureUrl,
                 String messageText,
                 String key);
}
