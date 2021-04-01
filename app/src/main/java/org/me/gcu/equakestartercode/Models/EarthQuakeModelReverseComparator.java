package org.me.gcu.equakestartercode.Models;

import java.util.Comparator;

public class EarthQuakeModelReverseComparator implements Comparator<EarthQuakeModel> {
    @Override
    public int compare(EarthQuakeModel first, EarthQuakeModel second) {
        Double magnitudeOne = first.getMagnitude();
        Double magnitudeTwo = second.getMagnitude();

        return magnitudeTwo.compareTo(magnitudeOne);
    }
}
