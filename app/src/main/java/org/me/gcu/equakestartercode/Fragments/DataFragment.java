package org.me.gcu.equakestartercode.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.ViewModels.DataViewModel;
import org.me.gcu.equakestartercode.ViewModels.DataViewModelFactory;
import org.me.gcu.equakestartercode.ViewModels.ListViewModel;
import org.me.gcu.equakestartercode.ViewModels.ListViewModelFactory;

public class DataFragment extends Fragment {
    private DataViewModel viewModel;
    private EarthQuakeModel earthQuakeModel;
    private TextView myLocationText;
    private TextView quakeLocationText;

    public DataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataViewModelFactory modelFactory = new DataViewModelFactory(getContext());
        viewModel = new ViewModelProvider(requireActivity(), modelFactory).get(DataViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        earthQuakeModel = getArguments().getParcelable("data");
        getLocation();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        myLocationText = (TextView) view.findViewById(R.id.currentLocationText);
        quakeLocationText = (TextView) view.findViewById(R.id.quakeLocationText);
        quakeLocationText.setText("Location of Quake- Lat:"+earthQuakeModel.getLat()+ " Long:"+earthQuakeModel.getLon());
        return view;
    }

    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        viewModel.findLocation().observe(getViewLifecycleOwner(), location -> {
            Log.e("location", "is: " + viewModel.findLocation().getValue().toString());
            myLocationText.setText("Lat:"+location.getLatitude() + " Long:"+location.getLongitude());
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == 1){
            viewModel.findLocation();
        }
    }
}