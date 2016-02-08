package de.marcuskrahl.dynamospielplan.test;

import de.marcuskrahl.dynamospielplan.CalendarAdapter;
import de.marcuskrahl.dynamospielplan.CalendarSync;
import de.marcuskrahl.dynamospielplan.HtmlMatchPlanRetriever;
import de.marcuskrahl.dynamospielplan.Match;
import de.marcuskrahl.dynamospielplan.MatchPlan;
import de.marcuskrahl.dynamospielplan.MatchPlanComparisonResult;
import de.marcuskrahl.dynamospielplan.MatchType;
import de.marcuskrahl.dynamospielplan.SyncRun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Calendar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/*
 * This seems to become an integration test
 * @TODO move it to other integration tests when implementing them
 */

public class SyncRunTest {

    private SyncRun syncRun;
    private HtmlMatchPlanRetriever mockMatchPlanRetriever;
    private CalendarAdapter mockAdapter;
    private CalendarSync mockCalendarSync;

    private Match matchToAdd;
    private Match matchToDelete;
    private Match matchToMove;
    private Match matchToMoveDestination;

    @Before
    public void Setup() {
        mockMatchPlanRetriever = mock(HtmlMatchPlanRetriever.class);
        mockAdapter = mock(CalendarAdapter.class);
        mockCalendarSync = mock(CalendarSync.class);

        syncRun = new SyncRun(mockMatchPlanRetriever,mockCalendarSync,mockAdapter);
    }

    @Test
    public void WhenARunStarts_SyncRun_GetsTheDataFromTheWebsite() throws Exception {
        setupDefaultMatchPlanReturn();

        syncRun.run();

        verify(mockMatchPlanRetriever).retrieve();
    }

    @Test
    public void WhenRunning_SyncRun_CallsCalendarSyncToUpdateChanges() throws Exception{
        ArgumentCaptor<MatchPlanComparisonResult> argument = ArgumentCaptor.forClass(MatchPlanComparisonResult.class);
        setupDefaultMatchPlanReturn();

        syncRun.run();

        verify(mockCalendarSync).run(argument.capture());
        assertEquals(matchToAdd, argument.getValue().matchesToAdd.get(0));
        assertEquals(matchToDelete, argument.getValue().matchesToDelete.get(0));
        assertEquals(matchToMove, argument.getValue().matchMovements.get(0).getMatch());
        assertEquals(matchToMoveDestination.getDate(), argument.getValue().matchMovements.get(0).getNewDate());
    }

    @Test
    public void WhenRunning_SyncRun_ReturnsTheComparisonResult() throws Exception {
        setupDefaultMatchPlanReturn();

        MatchPlanComparisonResult result = syncRun.run();

        assertEquals(matchToAdd, result.matchesToAdd.get(0));
        assertEquals(matchToDelete, result.matchesToDelete.get(0));
        assertEquals(matchToMove, result.matchMovements.get(0).getMatch());
        assertEquals(matchToMoveDestination.getDate(), result.matchMovements.get(0).getNewDate());
    }

    private void setupDefaultMatchPlanReturn() throws Exception{
        matchToAdd = new Match(MatchType.Test, "test opponent add", Calendar.getInstance(), true);
        matchToMove = new Match(MatchType.Test, "test opponent move", TestHelper.getLocalDate(2015, 5, 5, 10, 0), true);
        matchToMoveDestination = new Match(MatchType.Test, "test opponent move", TestHelper.getLocalDate(2015,5,6,10,0), true);
        matchToDelete = new Match(MatchType.Test, "test opponent delete", Calendar.getInstance(), true);

        MatchPlan newMatchPlan = new MatchPlan(new Match[] {matchToAdd, matchToMoveDestination});
        MatchPlan oldMatchPlan = new MatchPlan(new Match[] {matchToDelete, matchToMove});

        when(mockMatchPlanRetriever.retrieve()).thenReturn(newMatchPlan);
        when(mockAdapter.getExistingMatches()).thenReturn(oldMatchPlan);
    }
}
