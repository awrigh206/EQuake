package org.me.gcu.equakestartercode.Data;

import android.util.Xml;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class RemoteDataSource {
    private Future<List<EarthQuakeModel>> models;
    private ResourcePool resourcePool;
    private XmlParser xmlParser;

    @Inject
    public RemoteDataSource(ResourcePool resourcePool){
        ExecutorService executorService = resourcePool.getExecutorService();
        this.xmlParser = new XmlParser();
        this.models = executorService.submit(xmlParser);
        this.resourcePool = resourcePool;
    }
    public void updateModels(){
        this.models = resourcePool.getExecutorService().submit(xmlParser);
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

    public boolean hasData (){
        if(!getModels().isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
}
