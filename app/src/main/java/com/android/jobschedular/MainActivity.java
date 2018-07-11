package com.android.jobschedular;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import static android.os.Build.VERSION.SDK;
import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view){
        switch (view.getId()){
            case R.id.click:{
                if(SDK_INT> Build.VERSION_CODES.LOLLIPOP) {
                    createJobSchedulerJob();
                }else{
                    startFireBaseJob();
                }
                break;
            }
        }
    }

    int JOBID = 10;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createJobSchedulerJob() {
        ComponentName componentName = new ComponentName(this,JobServiceSample.class);

        JobInfo.Builder jobBuilder = new JobInfo.Builder(JOBID,componentName);
            jobBuilder.setMinimumLatency(1*1000);
            jobBuilder.setOverrideDeadline(3*1000);

            //jobBuilder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
            //jobBuilder.setRequiresDeviceIdle(true); // device should be idle
            //jobBuilder.setRequiresCharging(false); // we don't care if the device is charging or not

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobBuilder.build());
    }

    private void startFireBaseJob() {
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

        Job job = createJob(jobDispatcher);

        jobDispatcher.mustSchedule(job);

    }

    private Job createJob(FirebaseJobDispatcher jobDispatcher) {
        Job job = jobDispatcher.newJobBuilder()
                .setService(FirebaseJobServiceSample.class)
                .setTag("FIREBASE_JOB_0")
                //don't overwrite an existing job with the same tag
                .setReplaceCurrent(false)
                // We are mentioning that the job is periodic.
                .setRecurring(true)
                // Run between 30 - 60 seconds from now.
                .setTrigger(Trigger.executionWindow(30, 60))
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                //.setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                //Run this job only when the network is available.
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();

        return job;
    }

}
