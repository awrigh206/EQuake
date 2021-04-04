package org.me.gcu.equakestartercode.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.R;

public class EnergyFragment extends Fragment {
    private EarthQuakeModel model;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        model = getArguments().getParcelable("data");
        View view = inflater.inflate(R.layout.fragment_energy, container, false);
        doCalc();
        return view;
    }

    private void doCalc(){
        Double energy = calculateEnergyOfQuake();
        convertToTnt(energy);
        Double power = convertToWatts(energy,2.0);
        Log.e("Power",power.toString() + "Watts");
        Log.e("kettle run", kettleRunTime(power).toString()+"Hours");
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
        Double tons = energy/tonEnergy;
        Log.e("equiv", tons.toString()+"tons");
        return tons;
    }

    private Double convertToWatts(Double energy, Double time){
        return energy/time;
    }

    private Double kettleRunTime(double watts){
        double powerKettle = 2000;
        return watts/powerKettle;

    }
}