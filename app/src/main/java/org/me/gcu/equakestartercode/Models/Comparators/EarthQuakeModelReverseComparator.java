package org.me.gcu.equakestartercode.Models.Comparators;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.Comparator;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class EarthQuakeModelReverseComparator implements Comparator<EarthQuakeModel> {
    @Override
    public int compare(EarthQuakeModel first, EarthQuakeModel second) {
        Double magnitudeOne = first.getMagnitude();
        Double magnitudeTwo = second.getMagnitude();

        return magnitudeTwo.compareTo(magnitudeOne);
    }
}
