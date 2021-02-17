package org.me.gcu.equakestartercode.Models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EarthQuakeModelDao {
    @Query("SELECT * FROM EarthQuakeModel")
    List<EarthQuakeModel> getAll();

    @Query("SELECT * FROM EarthQuakeModel WHERE title LIKE :title LIMIT 1")
    EarthQuakeModel  findByTitle(String title);

    @Insert
    void insertAll(EarthQuakeModel earthQuakeModel);

    @Delete
    void delete (EarthQuakeModel model);
}
