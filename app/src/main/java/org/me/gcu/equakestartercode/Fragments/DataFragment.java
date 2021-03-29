package org.me.gcu.equakestartercode.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.ViewModels.DataViewModel;
import org.me.gcu.equakestartercode.ViewModels.DataViewModelFactory;
import org.me.gcu.equakestartercode.ViewModels.ListViewModel;
import org.me.gcu.equakestartercode.ViewModels.ListViewModelFactory;

import java.util.ArrayList;

public class DataFragment extends Fragment {
    private DataViewModel viewModel;
    private EarthQuakeModel earthQuakeModel;
    private TextView myLocationText;
    private TextView quakeLocationText;
    private LineChart chart;

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
        chart = (LineChart) view.findViewById(R.id.chart);
        quakeLocationText.setText("Location of Quake- Lat:"+earthQuakeModel.getLat()+ " Long:"+earthQuakeModel.getLon());
        setupLineChart();
        return view;
    }

    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        viewModel.findLocation().observe(getViewLifecycleOwner(), location -> {
            Log.e("location", "is: " + viewModel.findLocation().getValue().toString());
            myLocationText.setText("you are:"+viewModel.distanceToPoint(earthQuakeModel)+"m from this earthquakes location");
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == 1){
            viewModel.findLocation();
        }
    }

    private void setupLineChart(){
        chart.setBackgroundColor(Color.WHITE);
        chart.setGridBackgroundColor(Color.MAGENTA);
        chart.setDrawGridBackground(true);
        chart.setDrawBorders(true);
        chart.getDescription().setEnabled(true);
        chart.getDescription().setText("This is a chart");
        chart.getDescription().setTextSize(20f);

        chart.setPinchZoom(true);

        Legend l = chart.getLegend();
        l.setTextSize(20f);
        l.setEnabled(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(-0f);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setDrawGridLines(false);

        chart.getAxisRight().setEnabled(false);
        setChartData(earthQuakeModel.getMagnitude());
        chart.invalidate();
    }

    private void setChartData(Double magnitude){
        ArrayList<Entry> richterScaleValues = new ArrayList<>();
        for(int i =0; i<10; i++){
            if(i < magnitude && magnitude < i+1){
                richterScaleValues.add(new Entry(i,magnitude.floatValue()));
            }
            else if(i < magnitude){
                richterScaleValues.add(new Entry(i,i));
            }
            else if(i > magnitude){
                richterScaleValues.add(new Entry(i,i));
            }
            else{
                richterScaleValues.add(new Entry(i,magnitude.floatValue()));
            }
        }

        LineDataSet set;
        set = new LineDataSet(richterScaleValues,"Magnitude on the Scale");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.rgb(255, 241, 46));
        set.setDrawCircles(false);
        set.setLineWidth(2f);
        set.setCircleRadius(3f);
        set.setFillAlpha(255);
        set.setDrawFilled(true);
        set.setFillColor(Color.WHITE);
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setDrawCircleHole(false);
        set.setValueTextSize(20f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        LineData data = new LineData(dataSets);
        data.setDrawValues(true);

        // set data
        chart.setData(data);
    }
}