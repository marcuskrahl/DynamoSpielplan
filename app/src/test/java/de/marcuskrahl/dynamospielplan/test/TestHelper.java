package de.marcuskrahl.dynamospielplan.test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public abstract class TestHelper {

    public static Calendar getLocalDate(int year, int month, int day, int hour, int minute)
    {
        Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"));
        cal.set(year, month - 1, day, hour, minute,0);
        cal.set(cal.MILLISECOND,0);
        return cal;
    }
}
