package org.me.gcu.equakestartercode.Data;

import org.me.gcu.equakestartercode.AppModule;
import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;

public class RemoteDataSource {
    private Future<List<EarthQuakeModel>> models;

    @Inject
    public RemoteDataSource(ResourcePool resourcePool){
        ExecutorService executorService = resourcePool.getExecutorService();
        this.models = executorService.submit(new XmlParser());
    }
    public List<EarthQuakeModel> getModels() {
        if(models.isDone()){
            try {
                return models.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new LinkedList<>();
    }
}
