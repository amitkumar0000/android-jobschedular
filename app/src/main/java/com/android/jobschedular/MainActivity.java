package com.android.jobschedular;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
                    createJob();
                }else{
                    startFireBaseJob();
                }
                break;
            }
        }
    }

    int JOBID = 10;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createJob() {
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
    }

}
