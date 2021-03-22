package org.me.gcu.equakestartercode.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.ViewModels.ListViewModel;
import org.me.gcu.equakestartercode.ViewModels.ListViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {
//    ListViewModel listViewModel;
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
//            addMarkers(googleMap,listViewModel.getData().getValue());
            addMarkers(googleMap,dataList);
        }
    };

    private void addMarkers(GoogleMap map,List<EarthQuakeModel> modelList){
        LatLng last = new LatLng(0,0);
        for (EarthQuakeModel current : modelList){
            LatLng currentLocation = new LatLng(current.getLat(),current.getLon());
            map.addMarker(new MarkerOptions().position(currentLocation).title(current.getTitle()));
            last = currentLocation;
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(last));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dataList = (ArrayList)savedInstanceState.get("data");
//        ListViewModelFactory listViewModelFactory = new ListViewModelFactory();
//        listViewModel = (ListViewModel)new ViewModelProvider(this, listViewModelFactory).get(ListViewModel.class);
//        listViewModel.setContext(getContext());
//        Log.e("maps", "Lists: " +listViewModel.getData().getValue());
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