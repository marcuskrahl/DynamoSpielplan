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
    }

    private void setupDefaultMockMatchPlanRetriever() throws Exception {
        mockMatchPlanRetriever = mock(HtmlMatchPlanRetriever.class);
        when(mockMatchPlanRetriever.retrieve()).thenReturn(new MatchPlan(new Match[] {}));
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
        MatchPlan matchPlan = new MatchPlan(new Match[] {newMatch});
        when(mockMatchPlanRetriever.retrieve()).thenReturn(matchPlan);

        calendarSync.run();

        verify(mockAdapter).insertMatch(newMatch);

    }
}
