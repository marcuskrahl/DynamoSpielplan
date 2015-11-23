package de.marcuskrahl.dynamospielplan;

import de.marcuskrahl.dynamospielplan.exceptions.HtmlParseException;

import java.util.List;

public class CalendarSync {

    private MatchPlanComparer comparer;
    private CalendarAdapter calendarAdapter;
    private HtmlMatchPlanRetriever matchPlanRetriever;

    public CalendarSync(CalendarAdapter calendarAdapter, HtmlMatchPlanRetriever matchPlanRetriever) {
        this.calendarAdapter = calendarAdapter;
        this.matchPlanRetriever = matchPlanRetriever;
        this.comparer = new MatchPlanComparer();
    }

    public void run() throws HtmlParseException {
        if (!calendarAdapter.isCalendarCreated()){
            calendarAdapter.createCalendar();
        }

        MatchPlan newMatchPlan = matchPlanRetriever.retrieve();
        MatchPlan existingMatchPlan = calendarAdapter.getExistingMatches();

        MatchPlanComparisonResult comparisonResult = comparer.compare(existingMatchPlan,newMatchPlan);

        insertNewMatches(comparisonResult.matchesToAdd);
        deleteCancelledMatches(comparisonResult.matchesToDelete);
    }

    private void insertNewMatches(List<Match> matchesToInsert) {
        for(Match match: matchesToInsert) {
            calendarAdapter.insertMatch(match);
        }
    }

    private void deleteCancelledMatches(List<Match> matchesToDelete) {
        for (Match match: matchesToDelete) {
            calendarAdapter.deleteMatch(match);
        }
    }
}
