package org.me.gcu.equakestartercode.Models.Comparators;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.Comparator;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class EasterlyComparator implements Comparator<EarthQuakeModel> {
    @Override
    public int compare(EarthQuakeModel earthQuakeModel, EarthQuakeModel secondEarthQuakeModel) {
        Double firstLongitude = earthQuakeModel.getLon();
        Double secondLongitude = secondEarthQuakeModel.getLon();
        return secondLongitude.compareTo(firstLongitude);
    }

}
