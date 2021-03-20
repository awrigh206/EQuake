package org.me.gcu.equakestartercode.Data;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Repository {
    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;
    private List<EarthQuakeModel> earthQuakeModels;
    private MutableLiveData<List<EarthQuakeModel>> liveData;
    private final ResourcePool resourcePool;

    @Inject
    public Repository (RemoteDataSource remoteDataSource, LocalDataSource localDataSource,ResourcePool resourcePool){
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.resourcePool = resourcePool;
    }

    private void updateLocalDataWithRemoteData(){
        //This method updates the data inside the local database with the cached data from the remote datasource
        if(remoteDataSource.hasData()){
            localDataSource.updateData(remoteDataSource.getModels());
        }
    }

    public MutableLiveData<List<EarthQuakeModel>> getLiveData(boolean isOnline) {
        earthQuakeModels = getModels(isOnline);
        liveData = new MutableLiveData<>();
        liveData.setValue(getModels(isOnline));
        return liveData;
    }

    /**
     * Method to get Model data
     * remote data source is the preference but if it does not have data then it will fall back on local data source
     * If local data source does not have data then it will attempt to wait for Current data to be parsed
     * @return
     */
    public List<EarthQuakeModel> getModels(boolean online){
        try{
            if(online){
                if(remoteDataSource.hasData()){
                    Log.e("Source","Getting from remote datasource");
                    updateLocalDataWithRemoteData();
                    return remoteDataSource.getModels();
                }
                else if (localDataSource.hasData()){
                    Log.e("Source","Getting from Local data source");
                    return localDataSource.getModels();
                }
                else{
                    Log.e("Source","Getting direct from source");
                    return remoteDataSource.getModelsDirectFromSource();
                }
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
                            remoteDataSource.updateModels();
                            Thread.sleep(100);
                            if(remoteDataSource.hasData()){
                                localDataSource.updateData(remoteDataSource.getModels());
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 0, 100000);
    }

    public void setContext(Context context, boolean isOnline){
        this.localDataSource.setContext(context);
        setUpdateTimer(isOnline);
    }


    public RemoteDataSource getRemoteDataSource(){
        return this.remoteDataSource;
    }

    public LocalDataSource getLocalDataSource(){
        return this.localDataSource;}

}
