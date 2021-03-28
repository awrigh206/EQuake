package org.me.gcu.equakestartercode.ViewModels;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.me.gcu.equakestartercode.Data.ResourcePool;

import static android.content.Context.LOCATION_SERVICE;

public class DataViewModel extends ViewModel implements LocationListener {
    private ResourcePool resourcePool;
    private Context context;
    private MutableLiveData<Location> currentLocation = new MutableLiveData<>();

    public DataViewModel(ResourcePool resourcePool, Context context) {
        this.resourcePool = resourcePool;
        this.context = context;
    }

    public MutableLiveData<Location> findLocation() {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        return currentLocation;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocation.postValue(location);
    }
}
