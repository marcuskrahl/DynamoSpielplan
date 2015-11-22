package de.marcuskrahl.dynamospielplan.test;

import org.junit.Test;
import org.junit.Before;

import java.util.Calendar;

import de.marcuskrahl.dynamospielplan.CalendarAdapter;
import de.marcuskrahl.dynamospielplan.CalendarSync;
import de.marcuskrahl.dynamospielplan.HtmlMatchPlanRetriever;
import de.marcuskrahl.dynamospielplan.Match;
import de.marcuskrahl.dynamospielplan.MatchPlan;
import de.marcuskrahl.dynamospielplan.MatchType;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CalendarSyncTest {

    CalendarSync calendarSync;
    CalendarAdapter mockAdapter;
    HtmlMatchPlanRetriever mockMatchPlanRetriever;

    @Before
    public void Setup() throws Exception{
        setupDefaultMockAdapter();
        setupDefaultMockMatchPlanRetriever();
        calendarSync = new CalendarSync(mockAdapter,mockMatchPlanRetriever);
    }

    private void setupDefaultMockAdapter() {
        mockAdapter = mock(CalendarAdapter.class);
        when(mockAdapter.isCalendarCreated()).thenReturn(true);
        when(mockAdapter.getExistingMatches()).thenReturn(new MatchPlan(new Match[0]));
    }

    private void setupDefaultMockMatchPlanRetriever() throws Exception {
        mockMatchPlanRetriever = mock(HtmlMatchPlanRetriever.class);
        when(mockMatchPlanRetriever.retrieve()).thenReturn(new MatchPlan(new Match[0]));
    }

    @Test
    public void WhenRunAndNoCalendarIsPresent_CalendarSync_CreatesACalendar() throws Exception{
        when(mockAdapter.isCalendarCreated()).thenReturn(false);

        calendarSync.run();

        verify(mockAdapter).createCalendar();
    }

    @Test
    public void WhenRunAndCalendarIsPresent_CalendarSync_DoesNotCreateACalendar() throws Exception {
        when(mockAdapter.isCalendarCreated()).thenReturn(true);

        calendarSync.run();

        verify(mockAdapter,never()).createCalendar();
    }

    @Test
    public void WhenNewMatch_CalendarSync_AddsNewMatchToCalendar() throws Exception {
        Match newMatch = new Match(MatchType.Test,"Test opponent", Calendar.getInstance(),true);
        stubReturnOfOneMatch(newMatch);

        calendarSync.run();

        verify(mockAdapter).insertMatch(newMatch);
    }

    @Test
    public void WhenNewMatchAlreadyExists_CalendarSync_DoesNotAddNewMatchToCalendar() throws Exception {
        Match newMatch = new Match(MatchType.Test,"Test opponent", Calendar.getInstance(),true);
        stubReturnOfOneMatch(newMatch);
        stubExistingMatch(newMatch);

        calendarSync.run();

        verify(mockAdapter,never()).insertMatch(newMatch);
    }

    @Test
    public void WhenNewMatchDoesNotMatchExistingMatches_CalendarSync_AddsNewMatchToCalendar() throws Exception {
        Match newMatch = new Match(MatchType.Test,"Test opponent",Calendar.getInstance(),true);
        Match existingMatch = new Match(MatchType.Test,"another opponent",Calendar.getInstance(),true);
        stubReturnOfOneMatch(newMatch);
        stubExistingMatch(existingMatch);

        calendarSync.run();

        verify(mockAdapter).insertMatch(newMatch);

    }

    private void stubReturnOfOneMatch(Match matchToReturn) throws Exception{
        MatchPlan matchPlan = new MatchPlan(new Match[] {matchToReturn});
        when(mockMatchPlanRetriever.retrieve()).thenReturn(matchPlan);
    }

    private void stubExistingMatch(Match matchToReturn) {
        MatchPlan matchPlan = new MatchPlan(new Match[] {matchToReturn});
        when(mockAdapter.getExistingMatches()).thenReturn(matchPlan);
    }
}
