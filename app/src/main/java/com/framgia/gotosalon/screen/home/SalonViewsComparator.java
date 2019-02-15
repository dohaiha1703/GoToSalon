package com.framgia.gotosalon.screen.home;

import com.framgia.gotosalon.data.model.Salon;

import java.util.Comparator;

public class SalonViewsComparator implements Comparator<Salon> {
    private static final int EQUAL_RESULT = 0;
    private static final int BIGGER_RESULT = 1;
    private static final int SMALLER_RESULT = -1;

    @Override
    public int compare(Salon o1, Salon o2) {
        if (o1.getSalonView() == o2.getSalonView()) {
            return EQUAL_RESULT;
        } else if (o1.getSalonView() < o2.getSalonView()) {
            return BIGGER_RESULT;
        } else {
            return SMALLER_RESULT;
        }
    }
}
