package org.me.gcu.equakestartercode.ViewModels;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.me.gcu.equakestartercode.Data.ResourcePool;
import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import static android.content.Context.LOCATION_SERVICE;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class DataViewModel extends ViewModel {
    private ResourcePool resourcePool;
    private Context context;
    private MutableLiveData<Location> currentLocation = new MutableLiveData<>();

    public DataViewModel(ResourcePool resourcePool, Context context) {
        this.resourcePool = resourcePool;
        this.context = context;
    }

}
