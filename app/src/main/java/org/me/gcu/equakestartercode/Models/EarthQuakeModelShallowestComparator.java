package org.me.gcu.equakestartercode.Models;

import java.util.Comparator;

public class EarthQuakeModelShallowestComparator implements Comparator<EarthQuakeModel>  {
    @Override
    public int compare(EarthQuakeModel modelOne, EarthQuakeModel modelTwo) {
        Double depthOne = Double.parseDouble(modelOne.getDepth().split(" ")[1]);
        Double depthTwo = Double.parseDouble(modelTwo.getDepth().split(" ")[1]);
        return depthTwo.compareTo(depthOne);
    }
}
