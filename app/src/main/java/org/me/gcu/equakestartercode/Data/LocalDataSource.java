package org.me.gcu.equakestartercode.Data;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Inject;


public class LocalDataSource {
    private boolean hasData;
    private Context context;
    @Inject
    public LocalDataSource(){
//        AppDatabase db = Room.databaseBuilder(context,
//                AppDatabase.class, "local-db").build();
    }

    public boolean hasData() {
        return hasData;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
