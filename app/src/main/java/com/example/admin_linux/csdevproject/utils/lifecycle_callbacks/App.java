package com.example.admin_linux.csdevproject.utils.lifecycle_callbacks;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.util.Log;

public class App extends Application implements LifecycleObserver {

    private static boolean isInForeground = false;

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onEnterForeground() {
        isInForeground = true;
        Log.d("LC_test", "onEnterForeground");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onEnterBackground() {
        isInForeground = false;
        Log.d("LC_test", "onEnterBackground");
    }

    public static boolean isInForeground() {
        return isInForeground;
    }
}
