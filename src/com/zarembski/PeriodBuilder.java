package com.zarembski;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PeriodBuilder {
    public List<Period> buildPeriod(List<Date> startDateList, List<Date> endDateList) {
        List<Period> periodList = new LinkedList<>();

        //TODO implementacja
        int iteration = startDateList.size();
        Date temp = null;

        for (int i = 0; i < iteration; i++) {

            if (temp == null) {
                Period period = new Period(startDateList.get(0), endDateList.get(0));
                temp = addOneDay(endDateList.get(0));
                startDateList.remove(0);
                endDateList.remove(0);
                periodList.add(period);
            } else {
                if (startDateList.contains(temp)) {
                    Period period = new Period(temp, endDateList.get(0));
                    temp = addOneDay(endDateList.get(0));
                    startDateList.remove(0);
                    endDateList.remove(0);
                    periodList.add(period);
                } else {
                    Period period = new Period(temp, removeOneDay(startDateList.get(0)));
                    periodList.add(period);
                    period = new Period(startDateList.get(0), endDateList.get(0));
                    periodList.add(period);
                    temp = addOneDay(endDateList.get(0));
                    startDateList.remove(0);
                    endDateList.remove(0);
                }
            }
        }

        if (endDateList.size() > 0) {
            Period period = new Period(temp, endDateList.get(0));
            periodList.add(period);
        }

        return periodList;
    }

    private Date addOneDay(Date date) {
        return new Date(date.getTime() + TimeUnit.DAYS.toMillis(1));
    }

    private Date removeOneDay(Date date) {
        return new Date(date.getTime() + TimeUnit.DAYS.toMillis(-1));
    }
}
