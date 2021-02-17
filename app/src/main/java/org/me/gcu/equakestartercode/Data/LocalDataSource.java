package org.me.gcu.equakestartercode.Data;

import android.content.Context;

import androidx.room.Room;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.List;
import java.util.concurrent.Future;

import javax.inject.Inject;


public class LocalDataSource {
    private Context context;
    private List<EarthQuakeModel> models;
    private AppDatabase db;
    private boolean hasData;
    private ResourcePool resourcePool;
    private Future<List<EarthQuakeModel>> futureData;
    @Inject
    public LocalDataSource(ResourcePool resourcePool){
        this.resourcePool = resourcePool;
    }

    public void setContext(Context context) {
        this.context = context;
        this.db = Room.databaseBuilder(context,
                AppDatabase.class, "local-db").build();
        getStoredModels();
    }

    private void getStoredModels(){
        futureData = db.earthQuakeModelDao().getAll().toFuture();
    }

    public void refreshList(){
        try{
            if(futureData.isDone()){
                models = futureData.get();
                futureData = db.earthQuakeModelDao().getAll().toFuture();
            }

        }
        catch(Exception exception){
            exception.printStackTrace();
        }

    }

    public void updateData (List<EarthQuakeModel> models){
        db.earthQuakeModelDao().clearTable();
        for(EarthQuakeModel current : models){
            db.earthQuakeModelDao().insertAll(current);
        }
    }

    public boolean hasData() {
        return !models.isEmpty();
    }

    public List<EarthQuakeModel> getModels() {
        return models;
    }
}
