package com.zarembski;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Calendar cal = Calendar.getInstance();
        cal.set(2013, Calendar.JANUARY, 9); //Year, month and day of month
        Date date = cal.getTime();
        List<Date> startDate = new LinkedList<>();
        List<Date> endDate = new LinkedList<>();

        cal.set(2019, Calendar.JANUARY, 1);
        startDate.add(cal.getTime());
        startDate.add(cal.getTime());
        cal.set(2019, Calendar.JANUARY, 6);
        startDate.add(cal.getTime());
        cal.set(2019, Calendar.JANUARY, 12);
        startDate.add(cal.getTime());


        cal.set(2019, Calendar.JANUARY, 5);
        endDate.add(cal.getTime());
        cal.set(2019, Calendar.JANUARY, 9);
        endDate.add(cal.getTime());
        cal.set(2019, Calendar.JANUARY, 27);
        endDate.add(cal.getTime());
        cal.set(2019, Calendar.JANUARY, 30);
        endDate.add(cal.getTime());

        startDate = startDate.stream().distinct().sorted().collect(Collectors.toList());
        endDate = endDate.stream().distinct().sorted().collect(Collectors.toList());

        PeriodBuilder builder = new PeriodBuilder();
        List<Period> result = builder.buildPeriod(startDate, endDate);
        result.size();

    }
}
