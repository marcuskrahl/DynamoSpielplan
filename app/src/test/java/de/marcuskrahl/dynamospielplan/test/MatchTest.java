package de.marcuskrahl.dynamospielplan.test;

import org.junit.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.junit.Assert.*;

import de.marcuskrahl.dynamospielplan.Match;
import de.marcuskrahl.dynamospielplan.MatchType;

public class MatchTest {

    /**
     * Match is mostly data driven so almost no unit tests are necessary
     */

    @Test
    public void WhenTwoEqualMatchObjectsAreCompared_Match_ReturnsEqualTrue() {
        Match m1 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23));
        Match m2 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23));

        assertTrue(m1.equals(m2));
    }

    @Test
    public void WhenMatchIsComparedToDifferentClass_Match_ReturnsEqualFalse() {
        Match m1 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23));
        Object m2 = new String();

        assertFalse(m1.equals(m2));
    }

    @Test
    public void WhenMatchTypeDiffers_Match_ReturnsEqualFalse() {
        Match m1 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23));
        Match m2 = new Match(MatchType.Cup, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23));

        assertFalse(m1.equals(m2));
    }

    @Test
    public void WhenMatchOpponentDiffers_Match_ReturnsEqualFalse() {
        Match m1 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23));
        Match m2 = new Match(MatchType.League, "1. FC Magdeburg",getLocalDate(2015,7,23));

        assertFalse(m1.equals(m2));
    }

    @Test
    public void WhenMatchDateDiffers_Match_ReturnsEqualFalse() {
        Match m1 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23));
        Match m2 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,6,23));

        assertFalse(m1.equals(m2));
    }

    private Calendar getLocalDate(int year, int month, int day)
    {
        Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"));
        cal.set(year,month-1,day);
        return cal;
    }
}
