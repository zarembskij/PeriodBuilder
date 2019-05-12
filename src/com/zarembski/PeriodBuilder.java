package com.zarembski;

import com.sun.xml.internal.ws.util.StreamUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<Period> buildPeriodNew(List<Date> startDateList, List<Date> endDateList) {
        Date startDate = startDateList.stream().sorted().findFirst().get();
        Date endDate = endDateList.stream().sorted(Comparator.reverseOrder()).findFirst().get();

        Set<Pair> pairSet = Stream.concat(startDateList.stream().filter(d -> !d.equals(startDate)).map(d -> new Pair(removeOneDay(d), d)),
                endDateList.stream().filter(d -> !d.equals(endDate)).map(d -> new Pair(d, addOneDay(d)))).collect(Collectors.toCollection(TreeSet::new));

        return pairSet.size() > 0 ? makePeriodFromPair(startDate, endDate, pairSet) : Stream.of(new Period(startDate, endDate)).collect(Collectors.toList());
    }

    private List<Period> makePeriodFromPair(Date startDate, Date endDate, Set<Pair> pairSet) {
        List<Period> periodList = new LinkedList<>();

        periodList.add(new Period(startDate, pairSet.stream().findFirst().orElseThrow(() -> new RuntimeException(" ")).getStartDate()));
        Pair lastPair = pairSet.stream().reduce((a, b) -> {
            periodList.add(new Period(a.getEndDate(), b.getStartDate()));
            return b;
        }).orElseThrow(() -> new RuntimeException());
        periodList.add(new Period(lastPair.getEndDate(), endDate));

        return periodList;
    }
}
