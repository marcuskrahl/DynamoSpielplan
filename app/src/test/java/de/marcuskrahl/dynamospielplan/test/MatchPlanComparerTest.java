package de.marcuskrahl.dynamospielplan.test;

import org.junit.Test;
import org.junit.Before;

import java.util.Calendar;

import de.marcuskrahl.dynamospielplan.MatchPlan;
import de.marcuskrahl.dynamospielplan.Match;
import de.marcuskrahl.dynamospielplan.MatchPlanComparer;
import de.marcuskrahl.dynamospielplan.MatchPlanComparisonResult;
import de.marcuskrahl.dynamospielplan.MatchType;

import static org.junit.Assert.*;

public class MatchPlanComparerTest {

    private MatchPlanComparer comparer;

    @Before
    public void Setup() {
        comparer = new MatchPlanComparer();
    }

    @Test
    public void WhenNewMatchIsAddedToPlan_MatchPlanComparer_MarksMatchAsToBeAdded() {
        Match addedMatch = new Match(MatchType.Test, "Test match", Calendar.getInstance(), true);
        MatchPlan oldMatchPlan = new MatchPlan(new Match[0]);
        MatchPlan newMatchPlan = new MatchPlan(new Match[] {addedMatch});

        MatchPlanComparisonResult result = comparer.compare(oldMatchPlan, newMatchPlan);

        assertEquals(addedMatch, result.matchesToAdd.get(0));
    }

    @Test
    public void WhenOldMatchIsDeletedFromPlan_MatchPlanComparer_MarksMatchAsToBeDeleted() {
        Match deletedMatch = new Match(MatchType.Test, "Test match", Calendar.getInstance(), true);
        MatchPlan oldMatchPlan = new MatchPlan(new Match[] {deletedMatch});
        MatchPlan newMatchPlan = new MatchPlan(new Match[0]);

        MatchPlanComparisonResult result = comparer.compare(oldMatchPlan,newMatchPlan);

        assertEquals(deletedMatch, result.matchesToDelete.get(0));
    }

    @Test
    public void WhenNewMatchDoesNotEqualExistingMatches_MatchPlanComparer_MarksMatchAsToBeAdded() {
        Match addedMatch = new Match(MatchType.Test, "Test match", Calendar.getInstance(), true);
        Match oldMatch = new Match(MatchType.Test, "Test match 2", Calendar.getInstance(), true);
        MatchPlan oldMatchPlan = new MatchPlan(new Match[] {oldMatch});
        MatchPlan newMatchPlan = new MatchPlan(new Match[] {addedMatch});

        MatchPlanComparisonResult result = comparer.compare(oldMatchPlan, newMatchPlan);

        assertEquals(addedMatch, result.matchesToAdd.get(0));
    }

    @Test
    public void WhenMatchPlansAreTheSame_MatchPlanComparer_DoesNothing() {
        Match newMatch = new Match(MatchType.Test, "Test match", Calendar.getInstance(), true);
        Match oldMatch = new Match(MatchType.Test, "Test match", Calendar.getInstance(), true);
        MatchPlan oldMatchPlan = new MatchPlan(new Match[] {oldMatch});
        MatchPlan newMatchPlan = new MatchPlan(new Match[] {newMatch});

        MatchPlanComparisonResult result = comparer.compare(oldMatchPlan, newMatchPlan);

        assertEquals(0, result.matchesToAdd.size());
        assertEquals(0, result.matchesToDelete.size());
        assertEquals(0, result.matchMovements.size());

    }

    @Test
    public void WhenMatchIsRescheduled_MatchPlanComparer_MarksMatchAsToBeMoved() {
        Calendar newMatchDate = TestHelper.getLocalDate(2015,6,30,15,00);
        Match movedMatchNewSlot = new Match(MatchType.Test, "Test match", TestHelper.getLocalDate(2015, 6, 30, 15, 00) ,true);
        Match movedMatchOldSlot = new Match(MatchType.Test, "Test match", TestHelper.getLocalDate(2015, 6, 30, 13, 00) ,true);
        MatchPlan oldMatchPlan = new MatchPlan(new Match[] {movedMatchOldSlot});
        MatchPlan newMatchPlan = new MatchPlan(new Match[] {movedMatchNewSlot});

        MatchPlanComparisonResult result = comparer.compare(oldMatchPlan,newMatchPlan);

        assertEquals(movedMatchOldSlot, result.matchMovements.get(0).getMatch());
        assertEquals(newMatchDate, result.matchMovements.get(0).getNewDate());
        assertEquals(0, result.matchesToAdd.size());
        assertEquals(0, result.matchesToDelete.size());
    }

    @Test
    public void WhenThereAreTwoAlmostEqualMatches_MatchPlanComparer_DoesNotMarkMatchesAsToBeMoved() {
        Calendar newMatchDate = TestHelper.getLocalDate(2015,6,30,15,00);

        Match match1 = new Match(MatchType.Test, "Test match", TestHelper.getLocalDate(2015,6,30,15,00) ,true);
        Match match2 = new Match(MatchType.Test, "Test match", TestHelper.getLocalDate(2015,7,30,13,00) ,true);
        MatchPlan oldMatchPlan = new MatchPlan(new Match[] {match1, match2});
        MatchPlan newMatchPlan = new MatchPlan(new Match[] {match1, match2});

        MatchPlanComparisonResult result = comparer.compare(oldMatchPlan,newMatchPlan);

        assertEquals(0, result.matchMovements.size());
        assertEquals(0, result.matchesToAdd.size());
        assertEquals(0, result.matchesToDelete.size());
    }
}
