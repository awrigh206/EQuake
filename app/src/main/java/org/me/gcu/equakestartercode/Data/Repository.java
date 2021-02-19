package org.me.gcu.equakestartercode.Data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
@Singleton
public class Repository {
    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;

    @Inject
    public Repository (RemoteDataSource remoteDataSource, LocalDataSource localDataSource){
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public void updateLocalDataWithRemoteData(){
        //This method updates the data inside the local database with the cached data from the remote datasource
        if(remoteDataSource.hasData()){
            localDataSource.updateData(remoteDataSource.getModels());
        }
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
                return localDataSource.getModels();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void setContext(Context context){
        this.localDataSource.setContext(context);
    }


    public RemoteDataSource getRemoteDataSource(){
        return this.remoteDataSource;
    }

}
