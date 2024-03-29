package org.me.gcu.equakestartercode.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class DateHelper {
    private Map<String,Integer> monthMap;

    public DateHelper(){
        monthMap = new HashMap<>();
        monthMap.put("Jan",1);
        monthMap.put("Feb",2);
        monthMap.put("Mar",3);
        monthMap.put("Apr",4);
        monthMap.put("May",5);
        monthMap.put("June",6);
        monthMap.put("July",7);
        monthMap.put("Aug",8);
        monthMap.put("Sept",9);
        monthMap.put("Oct",10);
        monthMap.put("Nov",11);
        monthMap.put("Dec",12);
    }

    public Calendar parseStandardDate(String dateString){
        String relevantPart = dateString.split(", ")[1];
        String[] dateParts = relevantPart.split(" ");
        Integer day = Integer.parseInt(dateParts[0]);
        Integer month = convertMonthToNumber(dateParts[1]);
        Integer year = Integer.parseInt(dateParts[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar;
    }

    public Integer convertMonthToNumber(String code){
        return monthMap.get(code);
    }
}
