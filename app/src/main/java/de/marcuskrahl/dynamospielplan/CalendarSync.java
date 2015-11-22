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

        MatchPlan newMatchPlan = matchPlanRetriever.retrieve();
        MatchPlan existingMatchPlan = calendarAdapter.getExistingMatches();
        for(Match match: newMatchPlan.matches) {
            if (!matchAlreadyExists(match,existingMatchPlan)) {
                calendarAdapter.insertMatch(match);
            }
        }
    }

    private boolean matchAlreadyExists(Match match, MatchPlan existingMatchPlan) {
        for(Match existingMatch: existingMatchPlan.matches) {
            if (existingMatch.equals(match)) {
                return true;
            }
        }
        return false;
    }
}
