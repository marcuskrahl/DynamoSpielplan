package de.marcuskrahl.dynamospielplan;

import java.util.Calendar;

public class Match {

    private final MatchType matchType;

    private final String opponent;

    private final Calendar date;

    public Match(MatchType matchType, String opponent, Calendar date) {
        this.matchType = matchType;
        this.opponent = opponent;
        this.date = date;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof Match)) {
            return false;
        }

        Match otherMatch = (Match) otherObject;

        if (this.matchType != otherMatch.matchType) {
            return false;
        }

        if (!this.opponent.equals(otherMatch.opponent)) {
            return false;
        }

        if ( (this.date.get(Calendar.YEAR) != otherMatch.date.get(Calendar.YEAR))
            || (this.date.get(Calendar.MONTH) != otherMatch.date.get(Calendar.MONTH))
            || (this.date.get(Calendar.DATE) != otherMatch.date.get(Calendar.DATE))) {
            return false;
        }

        return true;
    }
}
