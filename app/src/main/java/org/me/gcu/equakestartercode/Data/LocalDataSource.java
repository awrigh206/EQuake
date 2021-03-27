package org.me.gcu.equakestartercode.Data;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.inject.Inject;


public class LocalDataSource {
    private Context context;
    private Future<List<EarthQuakeModel>> models;
    private AppDatabase db;
    private boolean hasData;
    private ResourcePool resourcePool;
    @Inject
    public LocalDataSource(ResourcePool resourcePool){
        this.resourcePool = resourcePool;
        this.models = null;
    }

    public void setContext(Context context) {
        this.context = context;
        this.db = Room.databaseBuilder(context,
                AppDatabase.class, "local-db").build();
        models = resourcePool.getExecutorService().submit(() -> db.earthQuakeModelDao().getAll());
    }

    public void updateData (List<EarthQuakeModel> models){
        resourcePool.getExecutorService().submit(() -> {
            db.earthQuakeModelDao().clearTable();
            db.earthQuakeModelDao().insertAll(models);
        });
    }

    public boolean hasData() {
        return models.isDone();
    }

    public Future<List<EarthQuakeModel>> getModels() throws ExecutionException, InterruptedException {
        return models;
    }
}
