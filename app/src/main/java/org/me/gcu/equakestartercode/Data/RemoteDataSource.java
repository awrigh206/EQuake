package org.me.gcu.equakestartercode.Data;

import androidx.lifecycle.MutableLiveData;

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

    public Future<List<EarthQuakeModel>> getModels() {
        return models;
    }

    public boolean hasData (){
        if(models != null){
            if(models.isDone()){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    public List<EarthQuakeModel> getModelsDirectFromSource(){
        this.models = resourcePool.getExecutorService().submit(xmlParser);
        try{
            while (!models.isDone()){
                android.os.SystemClock.sleep(100);
            }
            models.get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }
}
