package org.me.gcu.equakestartercode.Data;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class UpdateDataService extends JobService {
    private Repository repository;
    public UpdateDataService(Repository repository){
        this.repository = repository;
    }
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.e("Service", "Running the update service");
//        repository.getLocalDataSource().updateData();
//        repository.updateLocalDataWithRemoteData();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
