package de.marcuskrahl.dynamospielplan;

public interface CalendarAdapter {
    public boolean isCalendarCreated();

    public void createCalendar();

    public void insertMatch(Match matchToInsert);

    public MatchPlan getExistingMatches();
}
