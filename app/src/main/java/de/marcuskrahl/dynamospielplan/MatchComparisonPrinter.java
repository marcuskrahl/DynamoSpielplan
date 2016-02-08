package de.marcuskrahl.dynamospielplan;

import java.util.List;

public class MatchComparisonPrinter {

    private StringBuilder builder;

    public String print(MatchPlanComparisonResult comparisonResult) {
        builder = new StringBuilder();

        addNewMatches(comparisonResult.matchesToAdd);
        addDeletedMatches(comparisonResult.matchesToDelete);
        addMatchesToBeMoved(comparisonResult.matchMovements);

        return builder.toString();
    }

    private void addNewMatches(List<Match> newMatches) {
        printMatches(newMatches);
    }

    private void addDeletedMatches(List<Match> deletedMatches) {
        printMatches(deletedMatches);
    }

    private void addMatchesToBeMoved(List<MatchMovement> movements) {
        for(MatchMovement movement: movements) {
            builder.append(printMatch(movement.getMatch()));
            builder.append("\r\n");
        }
    }

    private void printMatches(List<Match> matchesToPrint) {
        for (Match match :
                matchesToPrint) {
            builder.append(printMatch(match));
            builder.append("\r\n");
        }
    }

    private String printMatch(Match match) {
        return String.format("%s (%s)",match.getOpponent(),match.getMatchType().toString());
    }
}
