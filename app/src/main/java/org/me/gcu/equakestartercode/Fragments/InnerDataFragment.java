package org.me.gcu.equakestartercode.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.R;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class InnerDataFragment extends Fragment {
    private TextView magText;
    private TextView dateText;
    private TextView locationText;
    private TextView energyText;
    private TextView kettleText;
    private TextView explosiveText;
    private EarthQuakeModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inner_data, container, false);
        Bundle args = getArguments();
        model = args.getParcelable("data");
        magText = ((TextView) view.findViewById(R.id.magText));
        dateText = (TextView) view.findViewById(R.id.dateText);
        locationText = (TextView) view.findViewById(R.id.locationText);
        energyText = (TextView) view.findViewById(R.id.energyText);
        kettleText = (TextView) view.findViewById(R.id.kettleText);
        explosiveText = (TextView) view.findViewById(R.id.explosiveText);

        dateText.setText("Date: " + model.getDateString());
        locationText.setText("Location: " + model.getLocation());
        magText.setText(String.valueOf("Magnitude: " + model.getMagnitude()));
        doCalc();

        return view;
    }

    private void doCalc(){
        Double energy = calculateEnergyOfQuake();
        energyText.setText("That's a whole: " + energy +" Joules of energy released!");
        explosiveText.setText("That's the equivalent of: " + Math.round(convertToTnt(energy)) + "KG of TNT!");
        Double power = convertToWatts(energy,3600.0);
        kettleText.setText("With that kind of power you could run a kettle for approxiamately: " + Math.round(kettleRunTime(power))+" Hours");
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
}