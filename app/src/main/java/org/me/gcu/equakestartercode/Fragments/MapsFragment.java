package org.me.gcu.equakestartercode.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.ViewModels.ListViewModel;
import org.me.gcu.equakestartercode.ViewModels.ListViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {
    ArrayList<EarthQuakeModel> dataList;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            addMarkers(googleMap,dataList);
        }
    };

    private void addMarkers(GoogleMap map,List<EarthQuakeModel> modelList) {
        LatLng last = new LatLng(0, 0);
        for (EarthQuakeModel current : modelList) {
            LatLng currentLocation = new LatLng(current.getLat(), current.getLon());
            MarkerOptions markerOptions = new MarkerOptions().position(currentLocation).title(current.getLocation()).snippet("Magnitude: " + current.getMagnitude());
            markerOptions.icon(getColour(current));
            map.setOnInfoWindowClickListener(marker -> {
                Log.e("map", "click on: " + findModel(marker.getTitle()));
                EarthQuakeModel model = findModel(marker.getTitle());
                Bundle bundle = new Bundle();
                bundle.putParcelable("data",model);
                Navigation.findNavController(getView()).navigate(R.id.action_mapsFragment_to_dataFragment,bundle);
            });
            map.addMarker(markerOptions);
            last = currentLocation;
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(last));
    }

    private EarthQuakeModel findModel (String location){
        for (EarthQuakeModel current : dataList){
            if(current.getLocation().equals(location)){
                return current;
            }
        }
        return null;
    }

    private BitmapDescriptor getColour(EarthQuakeModel model){
        if(model.getMagnitude() <= 2.0){
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        }
        else if(model.getMagnitude()  > 2.0 && model.getMagnitude()  <= 4.0){
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
        }
        else if(model.getMagnitude()  > 4.0){
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        }
        return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dataList = getArguments().getParcelableArrayList("data");
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}