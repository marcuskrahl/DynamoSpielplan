package de.marcuskrahl.dynamospielplan;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.List;
import java.util.ArrayList;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.net.Uri;
import android.database.Cursor;

public class CalendarAdapterImplementation implements CalendarAdapter {

    private long calendarID = -1;
    private boolean isCalendarAvailable = false;
    private ContentResolver contentResolver;

    private static final String CALENDAR_NAME = "Dynamo Kalender";
    private static final String ACCOUNT_NAME = "dynamo_calendar";

    private static final String TIME_ZONE = "Europe/Berlin";

    private static final long MILLISECONDS_IN_MINUTES = 60 * 1000;

    private static final String MATCH_TYPE_FIELD = Events.SYNC_DATA1;
    private static final String OPPONENT_FIELD = Events.SYNC_DATA2;
    private static final String IS_HOME_FIELD = Events.SYNC_DATA3;


    public static final String[] CALENDAR_LOOKUP_PROJECTION = new String[] {
            Calendars._ID,                           // 0
    };

    public static final String[] MATCH_QUERY_PROJECTION = new String[]{
            Events.DTSTART,
            MATCH_TYPE_FIELD,
            OPPONENT_FIELD,
            IS_HOME_FIELD,
    };

    private static final int CALENDAR_LOOKUP_ID_INDEX = 0;

    private static final int MATCH_QUERY_DATE_INDEX = 0;
    private static final int MATCH_QUERY_MATCH_TYPE_INDEX = 1;
    private static final int MATCH_QUERY_OPPONENT_INDEX = 2;
    private static final int MATCH_QUERY_IS_HOME_INDEX = 3;


    private static final String IS_HOME_VALUE = "true";
    private static final String IS_NOT_HOME_VALUE = "false";

    public CalendarAdapterImplementation(Context context) {
        this.contentResolver = context.getContentResolver();
    }

    public void insertMatch(Match matchToInsert) {
        try {
            createCalendarIfNecessary();

            long startMillis = 0;
            long endMillis = 0;
            Calendar beginTime = matchToInsert.getDate();
            beginTime.set(Calendar.SECOND,0);
            beginTime.set(Calendar.MILLISECOND,0);
            startMillis = beginTime.getTimeInMillis();
            endMillis = startMillis + ( 2 * 45 + 15) * MILLISECONDS_IN_MINUTES;

            String matchTitle = (matchToInsert.isHome() ? "Heim " : "Auswärts ") + matchToInsert.getOpponent();

            ContentValues values = new ContentValues();
            values.put(Events.DTSTART, startMillis);
            values.put(Events.DTEND, endMillis);
            values.put(Events.TITLE, matchTitle);
            values.put(Events.DESCRIPTION, matchTitle);
            values.put(Events.CALENDAR_ID, this.calendarID);
            values.put(Events.EVENT_TIMEZONE, TIME_ZONE);
            values.put(MATCH_TYPE_FIELD,matchToInsert.getMatchType().toString());
            values.put(OPPONENT_FIELD, matchToInsert.getOpponent());
            values.put(IS_HOME_FIELD, matchToInsert.isHome() ? IS_HOME_VALUE : IS_NOT_HOME_VALUE);

            Uri uri = getCalendarUri(Events.CONTENT_URI);

            contentResolver.insert(uri, values);
        } catch (SecurityException ex) {
            android.util.Log.e("permission","No permission to access calendar");
        }
    }

    private Uri getCalendarUri(Uri baseUri) {
        return baseUri.buildUpon()
                .appendQueryParameter(Calendars.ACCOUNT_NAME, "dynamo_calendar")
                .appendQueryParameter(Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL)
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .build();
    }

    public void deleteMatch(Match matchToDelete) {
        createCalendarIfNecessary();
    }

    public void moveMatch(Match matchToBeMoved, Calendar newDate) {
        createCalendarIfNecessary();
    }

    public MatchPlan getExistingMatches() {
        createCalendarIfNecessary();

        try {
            List<Match> matches = new ArrayList<Match>();
            Cursor cur = null;
            Uri uri = getCalendarUri(Events.CONTENT_URI);
            String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND ("
                    + Calendars.ACCOUNT_TYPE + " = ?))";
            String[] selectionArgs = new String[]{this.ACCOUNT_NAME, CalendarContract.ACCOUNT_TYPE_LOCAL};
            cur = contentResolver.query(uri, MATCH_QUERY_PROJECTION, selection, selectionArgs, null);
            while (cur.moveToNext()) {
                Calendar date = Calendar.getInstance();
                date.setTimeInMillis(cur.getLong(MATCH_QUERY_DATE_INDEX));
                MatchType matchType = MatchType.Test;
                try {
                    MatchType.valueOf(cur.getString(MATCH_QUERY_MATCH_TYPE_INDEX));
                } catch (Exception ex) {

                }
                String opponent = cur.getString(MATCH_QUERY_OPPONENT_INDEX);
                boolean isHome = cur.getString(MATCH_QUERY_IS_HOME_INDEX) == IS_HOME_VALUE;
                matches.add(new Match(matchType,opponent,date,isHome));
            }
            return new MatchPlan(matches.toArray(new Match[0]));
        } catch (SecurityException ex) {
            return new MatchPlan(new Match[0]);
        }
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
            Uri uri = getCalendarUri(Calendars.CONTENT_URI);
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

        Uri srcUri = getCalendarUri(Calendars.CONTENT_URI);

        Uri uri = contentResolver.insert(srcUri, values);
        this.calendarID = Long.parseLong(uri.getLastPathSegment());
    }
}
