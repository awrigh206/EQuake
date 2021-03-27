package org.me.gcu.equakestartercode.Data;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Repository {
    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;
    private MutableLiveData<List<EarthQuakeModel>> liveData;
    private final ResourcePool resourcePool;

    @Inject
    public Repository (RemoteDataSource remoteDataSource, LocalDataSource localDataSource,ResourcePool resourcePool){
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.resourcePool = resourcePool;
        this.liveData = new MutableLiveData<>();
    }

    public void setContext(Context context, boolean isOnline){
        this.localDataSource.setContext(context);
        try {
            this.liveData.postValue(getModels(true).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setUpdateTimer(isOnline);
    }

    private void updateLocalDataWithRemoteData() throws ExecutionException, InterruptedException {
        //This method updates the data inside the local database with the cached data from the remote datasource
        if(remoteDataSource.hasData()){
            localDataSource.updateData(remoteDataSource.getModels().get());
        }
    }

    public MutableLiveData<List<EarthQuakeModel>> getLiveData(boolean isOnline) {
        return liveData;
    }

    /**
     * Method to get Model data
     * remote data source is the preference but if it does not have data then it will fall back on local data source
     * If local data source does not have data then it will attempt to wait for Current data to be parsed
     * @return
     */
    public Future<List<EarthQuakeModel>> getModels(boolean online){
        try{
            if(online){
                Log.e("Source","Getting from remote datasource");
                updateLocalDataWithRemoteData();
                return remoteDataSource.getModels();
            }
            else{
                Log.e("Source","Getting from Local data source");
                return localDataSource.getModels();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private void setUpdateTimer(boolean isOnline){
        //updates the data inside the remote data source and the local datasource every ten seconds to enable up to date data on the front end
        Timer updateTimer = new Timer();
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                resourcePool.getExecutorService().execute(() -> {
                    try {
                        if(isOnline){
                            liveData.postValue(remoteDataSource.getModels().get());
                            remoteDataSource.updateModels();
                            Thread.sleep(100);
                            if(remoteDataSource.hasData()){
                                localDataSource.updateData(remoteDataSource.getModels().get());
                            }
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 0, 100000);
    }

    public RemoteDataSource getRemoteDataSource(){
        return this.remoteDataSource;
    }

    public LocalDataSource getLocalDataSource(){
        return this.localDataSource;}

}
