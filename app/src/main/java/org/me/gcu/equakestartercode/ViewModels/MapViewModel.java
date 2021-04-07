package org.me.gcu.equakestartercode.ViewModels;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import static android.content.Context.LOCATION_SERVICE;

public class MapViewModel extends ViewModel implements LocationListener {
    private Context context;
    private MutableLiveData<Location> currentLocation = new MutableLiveData<>();

    public MapViewModel(Context context){
        this.context = context;
        findLocation();
    }

    public float distanceToPoint(EarthQuakeModel quake){
        Location quakeLocation = new Location("");
        quakeLocation.setLatitude(quake.getLat());
        quakeLocation.setLongitude(quake.getLon());
        return currentLocation.getValue().distanceTo(quakeLocation);
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
