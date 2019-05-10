package com.zarembski;

import java.util.Date;

public class Pair extends Period implements Comparable<Pair>{
    public Pair(Date firstDate, Date secondDate) {
        super(firstDate, secondDate);
    }

    @Override
    public int compareTo(Pair pair) {
        return  this.getEndDate().compareTo(pair.getEndDate()) + this.getStartDate().compareTo(pair.getStartDate());
    }
}
