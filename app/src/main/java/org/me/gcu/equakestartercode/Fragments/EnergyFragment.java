package org.me.gcu.equakestartercode.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.R;

import java.util.ArrayList;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class EnergyFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private EarthQuakeModel model;
    private BarChart energyChart;
    private SeekBar seekBarX, seekBarY;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        model = getArguments().getParcelable("data");
        View view = inflater.inflate(R.layout.fragment_energy, container, false);
        setupEnergyChart(view);
        doCalc();
        return view;
    }

    private void doCalc(){
        Double energy = calculateEnergyOfQuake();
        convertToTnt(energy);
        Double power = convertToWatts(energy,2.0);
    }

    private Double calculateEnergyOfQuake(){
        Double result;
        Double magnitude = model.getMagnitude();
        result = 5.24 + 1.44 * magnitude;
        result = Math.pow(10,result);
        return result;
    }

    private Double convertToTnt(Double energy){
        Double tonEnergy = 4.184 * Math.pow(10,9);
        Double kiloEnergy = tonEnergy/1000;
        Double kilos = energy/kiloEnergy;
        return kilos;
    }

    private Double convertToWatts(Double energy, Double time){
        return energy/time;
    }

    private Double kettleRunTime(double watts){
        double powerKettle = 2000;
        return watts/powerKettle;

    }

    private void setupEnergyChart(View view){

        seekBarX = view.findViewById(R.id.xMax);
        seekBarX.setOnSeekBarChangeListener(this);

        seekBarY = view.findViewById(R.id.yMax);
        seekBarY.setOnSeekBarChangeListener(this);

        energyChart = view.findViewById(R.id.energyChart);

        energyChart.getDescription().setEnabled(false);
        energyChart.setMaxVisibleValueCount(60);
        energyChart.setPinchZoom(false);

        energyChart.setDrawBarShadow(false);
        energyChart.setDrawGridBackground(false);

        String[] labels = new String[]{"I", "Really", "Hate", "This"};
        XAxis xAxis = energyChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setTextSize(11.0f);

        energyChart.getAxisLeft().setDrawGridLines(false);

        seekBarX.setProgress(10);
        seekBarY.setProgress(100);
        energyChart.animateY(1500);
        energyChart.getLegend().setEnabled(false);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(0, convertToTnt(calculateEnergyOfQuake()).intValue()));
        Log.e("TNT", convertToTnt(calculateEnergyOfQuake()).toString());
        values.add(new BarEntry(1, 200));
        values.add(new BarEntry(2, 300));
        values.add(new BarEntry(3, 400));
        BarEntry entry = new BarEntry(4, 5000);

//        for (int i = 0; i < seekBarX.getProgress(); i++) {
////            float multi = (seekBarY.getProgress() + 1);
////            float val = (float) (Math.random() * multi) + multi / 3;
////            values.add(new BarEntry(i, val));
//            values.add(new BarEntry(i, convertToTnt(calculateEnergyOfQuake()).intValue()));
//            values.add(new BarEntry())
//        }

        BarDataSet set;

        if (energyChart.getData() != null && energyChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) energyChart.getData().getDataSetByIndex(0);
            set.setValues(values);
            energyChart.getData().notifyDataChanged();
            energyChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(values, "Data Set");
            set.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);

            BarData data = new BarData(dataSets);
            energyChart.setData(data);
            energyChart.setFitBars(true);
        }

        energyChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}