package de.marcuskrahl.dynamospielplan;

import de.marcuskrahl.dynamospielplan.exceptions.HtmlParseException;

import java.util.List;

public class CalendarSync {

    private CalendarAdapter calendarAdapter;

    public CalendarSync(CalendarAdapter calendarAdapter) {
        this.calendarAdapter = calendarAdapter;
    }

    public void run(MatchPlanComparisonResult comparisonResult) {
        insertNewMatches(comparisonResult.matchesToAdd);
        deleteCancelledMatches(comparisonResult.matchesToDelete);
        moveRescheduledMatches(comparisonResult.matchMovements);
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

    private void moveRescheduledMatches(List<MatchMovement> matchesToBeMoved) {
        for (MatchMovement movement: matchesToBeMoved) {
            calendarAdapter.moveMatch(movement.getMatch(),movement.getNewDate());
        }
    }
}
