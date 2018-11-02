package com.example.admin_linux.csdevproject.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.admin_linux.csdevproject.network.pojo.conversation_details.model.participants.CDParticipants;

import java.util.List;

public class ConversationDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<List<CDParticipants>> mList = new MutableLiveData<>();
    private MutableLiveData<List<ConversationDetailsMessage>> mListOfMessages = new MutableLiveData<>();

    public ConversationDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<CDParticipants>> getList() {
        return mList;
    }

    public void setList(List<CDParticipants> mList) {
        this.mList.setValue(mList);
    }

    public MutableLiveData<List<ConversationDetailsMessage>> getListOfMessages() {
        return mListOfMessages;
    }

    public void setListOfMessages(List<ConversationDetailsMessage> mListOfMessages) {
        this.mListOfMessages.setValue(mListOfMessages);
    }
}
