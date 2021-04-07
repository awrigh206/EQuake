package org.me.gcu.equakestartercode.Models.Comparators;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.Comparator;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class EarthQuakeModelComparator implements Comparator<EarthQuakeModel> {
    @Override
    public int compare(EarthQuakeModel first, EarthQuakeModel second) {
        Double firstMagnitude = first.getMagnitude();
        Double secondMagnitude = second.getMagnitude();
        return firstMagnitude.compareTo(secondMagnitude);
    }
}
