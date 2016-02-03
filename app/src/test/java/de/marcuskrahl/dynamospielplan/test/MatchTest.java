package de.marcuskrahl.dynamospielplan.test;

import org.junit.*;

import static de.marcuskrahl.dynamospielplan.test.TestHelper.*;

import static org.junit.Assert.*;

import de.marcuskrahl.dynamospielplan.Match;
import de.marcuskrahl.dynamospielplan.MatchType;

public class MatchTest {

    /**
     * Match is mostly data driven so almost no unit tests are necessary
     */

    @Test
    public void WhenTwoEqualMatchObjectsAreCompared_Match_ReturnsEqualTrue() {
        Match m1 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23,14,0),true);
        Match m2 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23,14,0),true);

        assertTrue(m1.equals(m2));
    }

    @Test
    public void WhenMatchIsComparedToDifferentClass_Match_ReturnsEqualFalse() {
        Match m1 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23,14,0),true);
        Object m2 = new String();

        assertFalse(m1.equals(m2));
    }

    @Test
    public void WhenMatchTypeDiffers_Match_ReturnsEqualFalse() {
        Match m1 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23,14,0),true);
        Match m2 = new Match(MatchType.Cup, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23,14,0),true);

        assertFalse(m1.equals(m2));
    }

    @Test
    public void WhenMatchOpponentDiffers_Match_ReturnsEqualFalse() {
        Match m1 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23,14,0),true);
        Match m2 = new Match(MatchType.League, "1. FC Magdeburg",getLocalDate(2015,7,23,14,0),true);

        assertFalse(m1.equals(m2));
    }

    @Test
    public void WhenMatchDateDiffers_Match_ReturnsEqualFalse() {
        Match m1 = new Match(MatchType.League, "SG Sonnenhof Großaspach", getLocalDate(2015, 7, 23,14,0),true);
        Match m2 = new Match(MatchType.League, "SG Sonnenhof Großaspach", getLocalDate(2015, 6, 23,14,0),true);

        assertFalse(m1.equals(m2));
    }

    @Test
    public void WhenMatchTimeDiffers_Match_ReturnsEqualFalse() {
        Match m1 = new Match(MatchType.League, "SG Sonnenhof Großaspach", getLocalDate(2015, 7, 23,14,0),true);
        Match m2 = new Match(MatchType.League, "SG Sonnenhof Großaspach", getLocalDate(2015, 7, 23,13,0),true);

        assertFalse(m1.equals(m2));
    }

    @Test
    public void WhenMatchHomeFlagDiffers_Match_ReturnsEqualFalse() {
        Match m1 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23,14,0),true);
        Match m2 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23,14,0),false);

        assertFalse(m1.equals(m2));
    }

    @Test
    public void WhenMatchesAreSame_Match_ReturnsEqualIgnoreDateTrue() {
        Match m1 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23,14,0),true);
        Match m2 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23,14,0),true);

        assertTrue(m1.equalsIgnoreDate(m2));
    }

    @Test
    public void WhenMatchDatesAreDifferent_Match_ReturnsEqualIgnoreDateTrue() {
        Match m1 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,23,14,0),true);
        Match m2 = new Match(MatchType.League, "SG Sonnenhof Großaspach",getLocalDate(2015,7,22,13,0),true);

        assertTrue(m1.equalsIgnoreDate(m2));
    }

    @Test
    public void WhenMatchOpponentIsNull_Match_ReturnsEqualFalse(){
        Match m1 = new Match(MatchType.League, null,getLocalDate(2015,7,23,14,0),true);
        Match m2 = new Match(MatchType.League, "SG Sonnenhof Großaspach", getLocalDate(2015,7,23,14,0),true);

        assertFalse(m1.equals(m2));
    }

}
