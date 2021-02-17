package org.me.gcu.equakestartercode.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.Models.EarthQuakeModelDao;

@Database(entities = {EarthQuakeModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EarthQuakeModelDao earthQuakeModelDao();
}
