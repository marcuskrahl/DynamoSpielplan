package de.marcuskrahl.dynamospielplan;

import de.marcuskrahl.dynamospielplan.exceptions.HtmlParseException;

public class SyncRun {

    private final HtmlMatchPlanRetriever matchPlanRetriever;
    private final CalendarSync calendarSync;
    private final CalendarAdapter calendarAdapter;

    private final MatchPlanComparer matchPlanComparer;

    public SyncRun(HtmlMatchPlanRetriever matchPlanRetriever, CalendarSync calendarSync, CalendarAdapter calendarAdapter) {
        this.matchPlanRetriever = matchPlanRetriever;
        this.calendarSync = calendarSync;
        this.calendarAdapter = calendarAdapter;

        this.matchPlanComparer = new MatchPlanComparer();
    }

    public MatchPlanComparisonResult run() throws HtmlParseException{
        MatchPlan newMatchPlan = matchPlanRetriever.retrieve();
        MatchPlan oldMatchPlan = calendarAdapter.getExistingMatches();

        MatchPlanComparisonResult comparisonResult = matchPlanComparer.compare(oldMatchPlan,newMatchPlan);

        calendarSync.run(comparisonResult);

        return comparisonResult;
    }
}
