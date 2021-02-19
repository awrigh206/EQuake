package org.me.gcu.equakestartercode.Data;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.room.Room;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


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
        db.close();
    }

    public void updateData (List<EarthQuakeModel> models){
        resourcePool.getExecutorService().submit(() -> {
            db.earthQuakeModelDao().clearTable();
            db.earthQuakeModelDao().insertAll(models);
            db.close();
        });
        Log.e("Local", String.valueOf(db.query("SELECT * FROM EarthQuakeModel;",new Object[0]).getCount()));
    }

    public boolean hasData() {
        return models.isDone();
    }

    public List<EarthQuakeModel> getModels() throws ExecutionException, InterruptedException {
        if(models.isDone()){
            return models.get();
        }
        else{
            while(!models.isDone()){
                android.os.SystemClock.sleep(100);
            }
            return models.get();
        }
    }
}
