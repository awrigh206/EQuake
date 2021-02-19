package org.me.gcu.equakestartercode.Models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface EarthQuakeModelDao {
    @Query("SELECT * FROM EarthQuakeModel")
    List<EarthQuakeModel> getAll();

    @Query("SELECT * FROM EarthQuakeModel WHERE title LIKE :title LIMIT 1")
    EarthQuakeModel findByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EarthQuakeModel earthQuakeModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EarthQuakeModel> earthQuakeModel);

    @Delete
    void delete (EarthQuakeModel model);

    @Query("DELETE FROM EarthQuakeModel")
    public void clearTable();
}
