package org.me.gcu.equakestartercode.Models.Comparators;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.Comparator;

/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class NotherlyComparator implements Comparator<EarthQuakeModel> {
    @Override
    public int compare(EarthQuakeModel earthQuakeModel, EarthQuakeModel secondEarthQuakeModel) {
        Double firstLatitude = earthQuakeModel.getLat();
        Double secondLatitude = secondEarthQuakeModel.getLat();
        return firstLatitude.compareTo(secondLatitude);
    }
}
