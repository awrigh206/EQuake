package org.me.gcu.equakestartercode.Data;

import android.content.Context;

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
    private List<EarthQuakeModel> data;

    @Inject
    public Repository (RemoteDataSource remoteDataSource, LocalDataSource localDataSource){
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.data = new LinkedList<>();
    }

    public void updateLocalDataWithRemoteData(){
        //This method updates the data inside the local database with the cached data from the remote datasource
        if(remoteDataSource.hasData()){
            localDataSource.updateData(remoteDataSource.getModels());
            data = remoteDataSource.getModels();
        }
        else{
            data = localDataSource.getModels();
        }

    }

    public List<EarthQuakeModel> getModels(){
        return remoteDataSource.getModels();
    }

    public void setContext(Context context){
        this.localDataSource.setContext(context);
    }

    public List<EarthQuakeModel> getLiveData() {
        return data;
    }

    public RemoteDataSource getRemoteDataSource(){
        return this.remoteDataSource;
    }
}
