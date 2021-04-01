package org.me.gcu.equakestartercode.Models;

import java.util.Comparator;

public class EarthQuakeModelComparator implements Comparator<EarthQuakeModel> {
    @Override
    public int compare(EarthQuakeModel first, EarthQuakeModel second) {
        Double firstMagnitude = first.getMagnitude();
        Double secondMagnitude = second.getMagnitude();
        return firstMagnitude.compareTo(secondMagnitude);
    }
}
