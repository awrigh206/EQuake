package org.me.gcu.equakestartercode.Data;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class UpdateDataService extends JobService {
    private Repository repository;
    public UpdateDataService(Repository repository){
        this.repository = repository;
    }
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        repository.getRemoteDataSource().updateModels();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
