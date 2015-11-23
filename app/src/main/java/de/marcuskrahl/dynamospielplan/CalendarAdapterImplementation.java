package de.marcuskrahl.dynamospielplan;

public abstract class CalendarAdapterImplementation implements CalendarAdapter{

    /*
     // The indices for the projection array above.
                int PROJECTION_ID_INDEX = 0;
                int PROJECTION_ACCOUNT_NAME_INDEX = 1;
                int PROJECTION_DISPLAY_NAME_INDEX = 2;
                int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

                ContentResolver cr = getContentResolver();
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
                Uri uri = cr.insert(srcUri, values);
                long id = Long.parseLong(uri.getLastPathSegment());
                Log.d("dynamospielplan",uri.toString());

                values = new ContentValues();
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
                Uri uri2 = cr.insert(Events.CONTENT_URI, values);

     */
}
