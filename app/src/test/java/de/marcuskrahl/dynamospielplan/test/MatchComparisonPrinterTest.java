package de.marcuskrahl.dynamospielplan.test;

import org.junit.*;

import de.marcuskrahl.dynamospielplan.Match;
import de.marcuskrahl.dynamospielplan.MatchType;
import de.marcuskrahl.dynamospielplan.MatchMovement;
import de.marcuskrahl.dynamospielplan.MatchComparisonPrinter;
import de.marcuskrahl.dynamospielplan.MatchPlanComparisonResult;

public class MatchComparisonPrinterTest {

    private MatchComparisonPrinter printer;

    @Before
    public void setup() {
        printer = new MatchComparisonPrinter();
    }

    @Test
    public void WhenCalledWithNoChanges_MatchComparisonPrinter_ReturnsEmptyString() {
        MatchPlanComparisonResult comparisonResult = new MatchPlanComparisonResult();

        String result = printer.print(comparisonResult);

        Assert.assertEquals("",result);
    }

    @Test
    public void WhenCalledWithNewMatches_MatchComparisonPrinter_ReturnsNewMatches() {
        MatchPlanComparisonResult comparisonResult = new MatchPlanComparisonResult();
        comparisonResult.matchesToAdd.add(new Match(MatchType.Test,"SG Sonnenhof Großaspach",TestHelper.getLocalDate(2015,10,5,14,0),true));
        comparisonResult.matchesToAdd.add(new Match(MatchType.League,"Chemnitzer FC",TestHelper.getLocalDate(2015,10,10,13,30),false));

        String result = printer.print(comparisonResult);

        Assert.assertTrue(result.contains("SG Sonnenhof Großaspach"));
        Assert.assertTrue(result.contains("Chemnitzer FC"));

    }

    @Test
    public void WhenCalledWithDeletedMatches_MatchComparisonPrinter_ReturnsDeletedMatches() {
        MatchPlanComparisonResult comparisonResult = new MatchPlanComparisonResult();
        comparisonResult.matchesToDelete.add(new Match(MatchType.Test,"SG Sonnenhof Großaspach",TestHelper.getLocalDate(2015,10,5,14,0),true));
        comparisonResult.matchesToDelete.add(new Match(MatchType.League,"Chemnitzer FC",TestHelper.getLocalDate(2015,10,10,13,30),false));

        String result = printer.print(comparisonResult);

        Assert.assertTrue(result.contains("SG Sonnenhof Großaspach"));
        Assert.assertTrue(result.contains("Chemnitzer FC"));
    }

    @Test
    public void WhenCalledWithMovedMatches_MatchComparisonPrinter_ReturnsMovedMatches() {
        MatchPlanComparisonResult comparisonResult = new MatchPlanComparisonResult();
        comparisonResult.matchMovements.add(new MatchMovement(new Match(MatchType.Test, "SG Sonnenhof Großaspach",TestHelper.getLocalDate(2015,10,5,14,0),true), TestHelper.getLocalDate(2015,10,6,14,0)));
        comparisonResult.matchMovements.add(new MatchMovement(new Match(MatchType.League,"Chemnitzer FC",TestHelper.getLocalDate(2015,10,10,13,30),false), TestHelper.getLocalDate(2015,10,10,13,0)));

        String result = printer.print(comparisonResult);

        Assert.assertTrue(result.contains("SG Sonnenhof Großaspach"));
        Assert.assertTrue(result.contains("Chemnitzer FC"));

    }
}
