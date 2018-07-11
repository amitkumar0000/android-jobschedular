# android-jobscheduler

for API 21 JobSchedular

for less than API 21 use firebase job dispatcher

How to user JobScheduler
    1. Create a class that extends JobService.
        it has two method 
            
            1. StartJob (JobParameters)
                
                return false to make it complete
                
                else return true as some background processing is going on
                
                     and call jobFinished(jobParameters, boolean) to inform system.
                     
                            in jobFinished return false to mark job has completed
                            
                                            else return true to schedule it again
                                            
                                            
            2. StopJob(  JobParameters)
               it is called by system it case job is cancelled. and clean up can be done here.
               return false as to drop this job
               else return true to reschedule it again.
  2. Create JOBInfo object to define the job and condition
        Condition like 
        jobBuilder.setMinimumLatency(1*1000); //Minimum time to run
        
        jobBuilder.setOverrideDeadline(3*1000); //maximum time to run
        
        jobBuilder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        
        jobBuilder.setRequiresDeviceIdle(true); // device should be idle
        
        jobBuilder.setRequiresCharging(false); // we don't care if the device is charging or not
        

  2.  Called JOB_SCHEDULER_SERVICE to schedule the job             
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobBuilder.build());
