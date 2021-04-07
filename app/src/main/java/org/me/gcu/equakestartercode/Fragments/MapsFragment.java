package org.me.gcu.equakestartercode.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
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
import org.me.gcu.equakestartercode.ViewModels.DataViewModel;
import org.me.gcu.equakestartercode.ViewModels.DataViewModelFactory;
import org.me.gcu.equakestartercode.ViewModels.ListViewModel;
import org.me.gcu.equakestartercode.ViewModels.ListViewModelFactory;
import org.me.gcu.equakestartercode.ViewModels.MapViewModel;
import org.me.gcu.equakestartercode.ViewModels.MapsViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {
    ArrayList<EarthQuakeModel> dataList;
    Float zoomLevel;
    private MapViewModel viewModel;
    private boolean showMyLocation;
    private Location userLocation;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            addMarkers(googleMap,dataList);
            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    if(userLocation != null){
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(userLocation.getLatitude(),userLocation.getLongitude())));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLocation.getLatitude(),userLocation.getLongitude()),zoomLevel));
                    }
                }
            });
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
        if(zoomLevel != null){
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(last,zoomLevel));
        }
        else{
            map.moveCamera(CameraUpdateFactory.newLatLng(last));
        }
    }

    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        viewModel.findLocation().observe(getViewLifecycleOwner(), location -> {
            Log.e("loc", "found user location");
            userLocation = location;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == 1){
            viewModel.findLocation();
        }
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
        MapsViewModelFactory modelFactory = new MapsViewModelFactory(getContext());
        viewModel = new ViewModelProvider(requireActivity(), modelFactory).get(MapViewModel.class);
        dataList = getArguments().getParcelableArrayList("data");
        zoomLevel = getArguments().getFloat("zoom");
        showMyLocation = getArguments().getBoolean("location");
        if(showMyLocation){
            getLocation();

        }
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