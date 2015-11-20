package de.marcuskrahl.dynamospielplan.test;

import org.junit.Test;
import org.junit.Before;

import de.marcuskrahl.dynamospielplan.CalendarAdapter;
import de.marcuskrahl.dynamospielplan.CalendarSync;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CalendarSyncTest {

    CalendarSync calendarSync;
    CalendarAdapter mockAdapter;

    @Before
    public void Setup() {
        mockAdapter = mock(CalendarAdapter.class);
        calendarSync = new CalendarSync(mockAdapter);
    }

    @Test
    public void WhenRunAndNoCalendarIsPresent_CalendarSync_CreatesACalendar() {
        when(mockAdapter.isCalendarCreated()).thenReturn(false);

        calendarSync.run();

        verify(mockAdapter).createCalendar();
    }

    @Test
    public void WhenRunAndCalendarIsPresent_CalendarSync_DoesNotCreateACalendar() {
        when(mockAdapter.isCalendarCreated()).thenReturn(true);

        calendarSync.run();

        verify(mockAdapter,never()).createCalendar();
    }
}
