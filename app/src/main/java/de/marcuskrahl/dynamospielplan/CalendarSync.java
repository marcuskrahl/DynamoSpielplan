package de.marcuskrahl.dynamospielplan;

import de.marcuskrahl.dynamospielplan.exceptions.HtmlParseException;

public class CalendarSync {

    private CalendarAdapter calendarAdapter;
    private HtmlMatchPlanRetriever matchPlanRetriever;

    public CalendarSync(CalendarAdapter calendarAdapter, HtmlMatchPlanRetriever matchPlanRetriever) {
        this.calendarAdapter = calendarAdapter;
        this.matchPlanRetriever = matchPlanRetriever;
    }

    public void run() throws HtmlParseException {
        if (!calendarAdapter.isCalendarCreated()){
            calendarAdapter.createCalendar();
        }

        MatchPlan matchPlan = matchPlanRetriever.retrieve();

        for(Match match: matchPlan.matches) {
            calendarAdapter.insertMatch(match);
        }
    }
}
