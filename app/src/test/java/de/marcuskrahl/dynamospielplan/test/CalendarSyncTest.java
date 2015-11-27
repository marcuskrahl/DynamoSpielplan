package de.marcuskrahl.dynamospielplan.test;

import org.junit.Test;
import org.junit.Before;

import java.util.Calendar;

import de.marcuskrahl.dynamospielplan.CalendarAdapter;
import de.marcuskrahl.dynamospielplan.CalendarSync;
import de.marcuskrahl.dynamospielplan.HtmlMatchPlanRetriever;
import de.marcuskrahl.dynamospielplan.Match;
import de.marcuskrahl.dynamospielplan.MatchMovement;
import de.marcuskrahl.dynamospielplan.MatchPlan;
import de.marcuskrahl.dynamospielplan.MatchPlanComparisonResult;
import de.marcuskrahl.dynamospielplan.MatchType;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CalendarSyncTest {

    CalendarSync calendarSync;
    CalendarAdapter mockAdapter;
    MatchPlanComparisonResult comparisonResult;

    @Before
    public void Setup() throws Exception{
        setupMockCalendarAdapter();
        comparisonResult = new MatchPlanComparisonResult();
        calendarSync = new CalendarSync(mockAdapter);
    }

    private void setupMockCalendarAdapter() {
        mockAdapter = mock(CalendarAdapter.class);
    }

    @Test
    public void WhenRunAndNoCalendarIsPresent_CalendarSync_CreatesACalendar() throws Exception{
        when(mockAdapter.isCalendarCreated()).thenReturn(false);

        calendarSync.run(comparisonResult);

        verify(mockAdapter).createCalendar();
    }

    @Test
    public void WhenRunAndCalendarIsPresent_CalendarSync_DoesNotCreateACalendar() throws Exception {
        when(mockAdapter.isCalendarCreated()).thenReturn(true);

        calendarSync.run(comparisonResult);

        verify(mockAdapter,never()).createCalendar();
    }

    @Test
    public void WhenNewMatch_CalendarSync_AddsNewMatchToCalendar() throws Exception {
        Match newMatch = new Match(MatchType.Test,"Test opponent", Calendar.getInstance(),true);
        comparisonResult.matchesToAdd.add(newMatch);

        calendarSync.run(comparisonResult);

        verify(mockAdapter).insertMatch(newMatch);
    }

    @Test
    public void WhenExistingMatchOughtToBeDeleted_CalendarSync_DeletesExistingMatchFromTheCalendar() throws Exception {
        Match existingMatch = new Match(MatchType.Test,"Test opponent",Calendar.getInstance(),true);
        comparisonResult.matchesToDelete.add(existingMatch);

        calendarSync.run(comparisonResult);

        verify(mockAdapter).deleteMatch(existingMatch);
    }

    @Test
    public void WhenExistingMatchIsMoved_CalendarSync_MovesExistingMatchInCalendar() throws  Exception {
        Match existingMatch = new Match(MatchType.Test,"Test opponent",Calendar.getInstance(),true);
        Calendar newDate = TestHelper.getLocalDate(2015,10,10,10,0);
        comparisonResult.matchMovements.add(new MatchMovement(existingMatch, newDate));

        calendarSync.run(comparisonResult);

        verify(mockAdapter).moveMatch(existingMatch,newDate);
    }

}
