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

        insertNewMatches(newMatchPlan,existingMatchPlan);
        deleteCancelledMatches(newMatchPlan,existingMatchPlan);
    }

    private void insertNewMatches(MatchPlan newMatchPlan, MatchPlan existingMatchPlan) {
        for(Match match: newMatchPlan.matches) {
            if (!matchExistsInPlan(match, existingMatchPlan)) {
                calendarAdapter.insertMatch(match);
            }
        }
    }

    private void deleteCancelledMatches(MatchPlan newMatchPlan, MatchPlan existingMatchPlan) {
        for (Match match: existingMatchPlan.matches) {
            if (!matchExistsInPlan(match, newMatchPlan)) {
                calendarAdapter.deleteMatch(match);
            }
        }
    }

    private boolean matchExistsInPlan(Match matchToSearch, MatchPlan matchPlan) {
        for(Match matchInPlan: matchPlan.matches) {
            if (matchInPlan.equals(matchToSearch)) {
                return true;
            }
        }
        return false;
    }
}
