package de.marcuskrahl.dynamospielplan;

import java.util.Calendar;

public interface CalendarAdapter {
    public boolean isCalendarCreated();

    public void createCalendar();

    public void insertMatch(Match matchToInsert);

    public void deleteMatch(Match matchToDelete);

    public void moveMatch(Match matchToBeMoved, Calendar newDate);

    public MatchPlan getExistingMatches();
}
