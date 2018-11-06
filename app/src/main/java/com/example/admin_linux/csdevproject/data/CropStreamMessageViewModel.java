package com.example.admin_linux.csdevproject.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.admin_linux.csdevproject.data.models.CropStreamMessage;

import java.util.List;

public class CropStreamMessageViewModel extends AndroidViewModel {

    private MutableLiveData<List<CropStreamMessage>> mList = new MutableLiveData<>();

    public CropStreamMessageViewModel(@NonNull Application application) {
        super(application);
    }

    public List<CropStreamMessage> getNormalList(){
        return mList.getValue();
    }

    public MutableLiveData<List<CropStreamMessage>> getList() {
        return mList;
    }

    public void setList(List<CropStreamMessage> mList) {
        this.mList.postValue(mList);
    }
}
