package org.me.gcu.equakestartercode.Models.Comparators;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.Comparator;

/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class WesterlyComparator implements Comparator<EarthQuakeModel> {
    @Override
    public int compare(EarthQuakeModel earthQuakeModel, EarthQuakeModel secondEarthQuakeModel) {
        Double firstLongitude = earthQuakeModel.getLon();
        Double secondLongitude = secondEarthQuakeModel.getLon();
        return firstLongitude.compareTo(secondLongitude);
    }
}
