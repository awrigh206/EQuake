package org.me.gcu.equakestartercode.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.R;

import java.util.ArrayList;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class EnergyFragment extends Fragment implements OnChartValueSelectedListener {
    private EarthQuakeModel model;
    private BarChart energyChart;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        model = getArguments().getParcelable("data");
        View view = inflater.inflate(R.layout.fragment_energy, container, false);
        setupEnergyChart(view);
        return view;
    }



    private void setupEnergyChart(View view){
        energyChart = view.findViewById(R.id.energyChart);

        energyChart.getDescription().setEnabled(false);
        energyChart.setMaxVisibleValueCount(60);
        energyChart.setPinchZoom(true);

        energyChart.setDrawBarShadow(true);
        energyChart.setDrawGridBackground(false);


        XAxis xAxis = energyChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(11.0f);

        energyChart.getAxisLeft().setDrawGridLines(false);
        addData();
        energyChart.animateY(1500);
        energyChart.getLegend().setEnabled(false);
        energyChart.setOnChartValueSelectedListener(this);
    }

    private void addData(){
        String[] labels = new String[]{"This Quake","Haiti","Tangshan","Indian"};
        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(0,(float)model.getMagnitude()));
        values.add(new BarEntry(1,7.0f));
        values.add(new BarEntry(2,7.5f));
        values.add(new BarEntry(3,9.1f));
        BarDataSet set;

        if (energyChart.getData() != null && energyChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) energyChart.getData().getDataSetByIndex(0);
            set.setValues(values);
            energyChart.getData().notifyDataChanged();
            energyChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(values, "EarthQuakes");
            set.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);

            BarData data = new BarData(dataSets);
            energyChart.setData(data);
            energyChart.setFitBars(false);
        }
        energyChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        energyChart.getXAxis().setDrawLabels(false);
        energyChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        int x = (int) e.getX();

        switch (x){
            case 0:
                Log.e("chart", "You have clicked on the quake model");
                Toast.makeText(getActivity(),"This Earthquake",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(getActivity(),"Hatian Earthquake",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getActivity(),"Tangshan Earthquake",Toast.LENGTH_SHORT).show();
                //tangshan
                break;
            case 3:
                Toast.makeText(getActivity(),"Indian Ocean Earthquake",Toast.LENGTH_SHORT).show();
                //indian ocean
                break;
        }
    }

    @Override
    public void onNothingSelected() {

    }
}