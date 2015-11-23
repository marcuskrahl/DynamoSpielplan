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

    }
}
