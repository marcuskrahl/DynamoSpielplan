package de.marcuskrahl.dynamospielplan;

import java.util.Calendar;
import java.util.TimeZone;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.net.Uri;
import android.database.Cursor;

public class CalendarAdapterImplementation implements CalendarAdapter {

    private long calendarID = -1;
    private boolean isCalendarAvailable = false;
    private ContentResolver contentResolver;

    private static final String CALENDAR_NAME = "Dynamo Kalender";
    private static final String ACCOUNT_NAME = "dynamo_calendar";

    private static final String TIME_ZONE = "Europe/Berlin";

    public static final String[] CALENDAR_LOOKUP_PROJECTION = new String[] {
            Calendars._ID,                           // 0
    };

    private static final int CALENDAR_LOOKUP_ID_INDEX = 0;

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
        return null;
    }

    private void createCalendarIfNecessary() {
        if (!isCalendarAvailable) {
            if (!isCalendarCreated()) {
                createCalendar();
            }
        }
    }

    private boolean isCalendarCreated() {
        try {
            Cursor cur = null;
            Uri uri = Calendars.CONTENT_URI;
            String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND ("
                    + Calendars.ACCOUNT_TYPE + " = ?))";
            String[] selectionArgs = new String[]{this.ACCOUNT_NAME, CalendarContract.ACCOUNT_TYPE_LOCAL};
            cur = contentResolver.query(uri, CALENDAR_LOOKUP_PROJECTION, selection, selectionArgs, null);
            while (cur.moveToNext()) {
                this.calendarID = cur.getLong(CALENDAR_LOOKUP_ID_INDEX);
                return true;
            }
            return false;
        } catch (SecurityException ex) {
            return false;
        }
    }

    private void createCalendar() {
        ContentValues values = new ContentValues();
        values.put(Calendars.ACCOUNT_NAME, this.ACCOUNT_NAME);
        values.put(Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(Calendars.NAME,this.CALENDAR_NAME);
        values.put(Calendars.CALENDAR_DISPLAY_NAME, this.CALENDAR_NAME);
        values.put(Calendars.SYNC_EVENTS,1);
        values.put(Calendars.VISIBLE,1);
        values.put(Calendars.CALENDAR_ACCESS_LEVEL,Calendars.CAL_ACCESS_OWNER);
        values.put(Calendars.DIRTY,1);
        values.put(Calendars.CALENDAR_TIME_ZONE, TIME_ZONE);

        Uri srcUri = Calendars.CONTENT_URI.buildUpon()
                .appendQueryParameter(Calendars.ACCOUNT_NAME, "dynamo_calendar")
                .appendQueryParameter(Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL)
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true").build();
        Uri uri = contentResolver.insert(srcUri, values);
        this.calendarID = Long.parseLong(uri.getLastPathSegment());
    }
}
