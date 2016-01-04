package de.marcuskrahl.dynamospielplan;

import java.util.Calendar;
import java.util.TimeZone;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.net.Uri;

public class CalendarAdapterImplementation implements CalendarAdapter {

    private long calendarID = -1;
    private boolean isCalendarAvailable = false;
    private ContentResolver contentResolver;

    public CalendarAdapterImplementation(Context context) {
        this.contentResolver = context.getContentResolver();
    }

    public void insertMatch(Match matchToInsert) {
        createCalendarIfNecessary();

        /*ContentValues values = new ContentValues();
        Calendar start = Calendar.getInstance();
        start.set(2015,9,29,4,0,0);
        Calendar end = Calendar.getInstance();
        end.set(2015,9,29,20,0,0);
        values.put(Events.DTSTART, start.getTimeInMillis());
        values.put(Events.DTEND,end.getTimeInMillis());
        values.put(Events.TITLE, "Spiel");
        values.put(Events.DESCRIPTION, "Spiel");
        values.put(Events.CALENDAR_ID, id);
        values.put(Events.EVENT_TIMEZONE, "America/Los_Angeles");
        Uri uri2 = contentResolver.insert(Events.CONTENT_URI, values);*/
    }

    public void deleteMatch(Match matchToDelete) {
        createCalendarIfNecessary();
    }

    public void moveMatch(Match matchToBeMoved, Calendar newDate) {
        createCalendarIfNecessary();
    }

    public MatchPlan getExistingMatches() {
        createCalendarIfNecessary();
    }

    private void createCalendarIfNecessary() {
        if (!isCalendarAvailable) {
            if (!isCalendarCreated()) {
                createCalendar();
            }
        }
    }

    private boolean isCalendarCreated() {
        return false;
    }

    private void createCalendar() {
        ContentValues values = new ContentValues();
        values.put(Calendars.ACCOUNT_NAME, "dynamo_calendar");
        values.put(Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(Calendars.NAME, "Dynamo Kalender");
        values.put(Calendars.CALENDAR_DISPLAY_NAME, "Dynamo Kalender");
        values.put(Calendars.SYNC_EVENTS,1);
        values.put(Calendars.VISIBLE,1);
        values.put(Calendars.CALENDAR_ACCESS_LEVEL,Calendars.CAL_ACCESS_OWNER);
        values.put(Calendars.DIRTY,1);
        values.put(Calendars.CALENDAR_TIME_ZONE, TimeZone.getTimeZone("Europe/Berlin").getID());
        //values.put(CalendarContract.CALLER_IS_SYNCADAPTER, "true");
        Uri srcUri = Calendars.CONTENT_URI.buildUpon()
                .appendQueryParameter(Calendars.ACCOUNT_NAME, "dynamo_calendar")
                .appendQueryParameter(Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL)
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true").build();
        Uri uri = contentResolver.insert(srcUri, values);
        this.calendarID = Long.parseLong(uri.getLastPathSegment());
    }
}
