package com.android.jobschedular;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobServiceSample extends JobService{
    String TAG = "JobServiceSample";
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG,"onStartJob");
        if(Looper.myLooper() == Looper.getMainLooper()){
            Log.d(TAG," main Thread");
        }else{
            Log.d(TAG," background thread");
        }
        //Return false if job has been completed.
//        return false;
        //Return true to tell there is some background task going on.
        new PrintAsynckTask().execute(jobParameters);
        return true;

    }

    //is called by the system if the job is cancelled before being finished.
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG,"onStopJob");
        //return true if you’d like the system to reschedule the job, or
        // false if it doesn’t matter and the system will drop this job
        return false;
    }

    class PrintAsynckTask extends AsyncTask<JobParameters, Integer, JobParameters> {

        @Override
        protected JobParameters doInBackground(JobParameters... jobParameters) {
            for(int i=0; i <5 ; i++){
                Log.d(TAG,""+i);
            }
            return jobParameters[0];
        }

        @Override
        protected void onPostExecute(JobParameters param) {
            super.onPostExecute(param);
//            jobFinished(param,true);
            //return true if job need to reschdule again
            //else
            //return false to mark job complete
            jobFinished(param,false);



        }
    }
}
