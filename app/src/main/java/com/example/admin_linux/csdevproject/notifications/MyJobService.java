package com.example.admin_linux.csdevproject.notifications;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyJobService extends JobService {

    private static final String TAG = "NS_test";

    @Override
    public boolean onStartJob(JobParameters job) {
        // Do some work here
        Log.d(TAG, "Performing long running task in scheduled job");
        return false; // Answers the question: "Is there still work going on?"
    }

    @Override
    public boolean onStopJob(JobParameters job) {

        return false; // Answers the question: "Should this job be retried?"
    }
}