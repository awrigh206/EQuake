package org.me.gcu.equakestartercode.Data;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.LinkedList;
import java.util.List;
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
    private List<EarthQuakeModel> models;
    private AppDatabase db;
    private boolean hasData;
    private ResourcePool resourcePool;
    private Single<List<EarthQuakeModel>> futureData;
    @Inject
    public LocalDataSource(ResourcePool resourcePool){

        this.resourcePool = resourcePool;
        this.models = new LinkedList<>();
    }

    public void setContext(Context context) {
        this.context = context;
        this.db = Room.databaseBuilder(context,
                AppDatabase.class, "local-db").build();
        futureData = db.earthQuakeModelDao().getAll();
        futureData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<EarthQuakeModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(List<EarthQuakeModel> earthQuakes) {
                        // update values
                        models = earthQuakes;
                        Log.e("Local", earthQuakes.toString());
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                });
        db.close();
    }

    public void updateData (List<EarthQuakeModel> models){
        db.earthQuakeModelDao().clearTable();
        db.earthQuakeModelDao().insertAll(models);
        Log.e("Local", String.valueOf(db.query("SELECT * FROM EarthQuakeModel;",new Object[0]).getCount()));
        db.close();
    }

    public boolean hasData() {
        return !models.isEmpty();
    }

    public List<EarthQuakeModel> getModels() {
        return models;
    }
}
