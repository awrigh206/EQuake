package org.me.gcu.equakestartercode.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.ViewModels.DataViewModel;
import org.me.gcu.equakestartercode.ViewModels.DataViewModelFactory;

import java.util.ArrayList;

public class DataFragment extends Fragment {
    private DataViewModel viewModel;
    private EarthQuakeModel earthQuakeModel;
    private TextView myLocationText;
    private TextView quakeLocationText;
    private TabLayout tabLayout;
    private LineChart chart;
    private ViewPager2 pager;

    public DataFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        myLocationText = (TextView) view.findViewById(R.id.currentLocationText);
        quakeLocationText = (TextView) view.findViewById(R.id.quakeLocationText);
        pager = (ViewPager2) view.findViewById(R.id.pager);
        chart = (LineChart) view.findViewById(R.id.chart);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        PagerCollectionAdapter adapter = new PagerCollectionAdapter(this,earthQuakeModel);
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.e("Page", String.valueOf(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        pager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, pager,
                (tab, position) -> tab.setText("Page " + (position + 1))
        ).attach();
        setupLineChart();
        return view;
    }

    private void setupLineChart(){
        chart.setBackgroundColor(Color.WHITE);
        chart.setGridBackgroundColor(Color.MAGENTA);
        chart.setDrawGridBackground(false);
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
        leftAxis.setAxisMaximum(1f);
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
        Double logMag = Math.log10(magnitude);
        Log.e("mag",magnitude.toString());
        Log.e("log mag", logMag.toString());
        for(int i =0; i<10; i++){
            if(i < magnitude && magnitude < i+1){
                richterScaleValues.add(new Entry(i,logMag.floatValue()));
            }
            else if(i < magnitude){
                richterScaleValues.add(new Entry(i,(float) Math.log10(i)));
            }
            else if(i > magnitude){
                richterScaleValues.add(new Entry(i,(float) Math.log10(i)));
            }
            else{
                richterScaleValues.add(new Entry(i,logMag.floatValue()));
            }
        }

        LineDataSet set;
        set = new LineDataSet(richterScaleValues,"Magnitude on the Scale");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.rgb(255, 241, 46));
        set.setDrawCircles(false);
        set.setLineWidth(0.1f);
        set.setCircleRadius(0.1f);
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