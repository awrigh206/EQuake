package org.me.gcu.equakestartercode.Models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface EarthQuakeModelDao {
    @Query("SELECT * FROM EarthQuakeModel")
    Single<List<EarthQuakeModel>> getAll();

    @Query("SELECT * FROM EarthQuakeModel WHERE title LIKE :title LIMIT 1")
    Single<EarthQuakeModel> findByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(EarthQuakeModel earthQuakeModel);

    @Delete
    Completable delete (EarthQuakeModel model);

    @Query("DELETE FROM EarthQuakeModel")
    public Single<Integer> clearTable();
}
