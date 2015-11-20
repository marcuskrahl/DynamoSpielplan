package de.marcuskrahl.dynamospielplan;


public class CalendarSync {

    private CalendarAdapter calendarAdapter;

    public CalendarSync(CalendarAdapter calendarAdapter) {
        this.calendarAdapter = calendarAdapter;
    }

    public void run() {
        if (!calendarAdapter.isCalendarCreated()) {
            calendarAdapter.createCalendar();
        }
    }
}
