package org.me.gcu.equakestartercode.Models.Comparators;

import org.me.gcu.equakestartercode.Data.DateHelper;
import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.Comparator;
import java.util.Date;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class DateComparator implements Comparator<EarthQuakeModel> {
    @Override
    public int compare(EarthQuakeModel earthQuakeModel, EarthQuakeModel earthQuakeModelTwo) {
        Date firstDate;
        Date secondDate;
        DateHelper helper = new DateHelper();
        return 0;
    }
}
