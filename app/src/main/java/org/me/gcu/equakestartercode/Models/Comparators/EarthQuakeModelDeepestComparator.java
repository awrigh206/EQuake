package org.me.gcu.equakestartercode.Models.Comparators;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.Comparator;

public class EarthQuakeModelDeepestComparator implements Comparator<EarthQuakeModel> {
    @Override
    public int compare(EarthQuakeModel modelOne, EarthQuakeModel modelTwo) {
        Double depthOne = Double.parseDouble(modelOne.getDepth().split(" ")[1]);
        Double depthTwo = Double.parseDouble(modelTwo.getDepth().split(" ")[1]);
        return depthOne.compareTo(depthTwo);
    }
}
