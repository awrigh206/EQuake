package org.me.gcu.equakestartercode.Data;

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

    public List<EarthQuakeModel> getModels(){
        return remoteDataSource.getModels();
    }
}
