package de.marcuskrahl.dynamospielplan;

import java.util.Calendar;

public class Match {

    private final MatchType matchType;

    private final String opponent;

    private final Calendar date;

    private final boolean home;

    public Calendar getDate() {
        return date;
    }

    public String getOpponent() {
        return opponent;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public Match(MatchType matchType, String opponent, Calendar date, boolean isHome) {
        this.matchType = matchType;
        this.opponent = opponent;
        this.date = date;
        this.home = isHome;
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
                || (this.date.get(Calendar.DATE) != otherMatch.date.get(Calendar.DATE))
                || (this.date.get(Calendar.HOUR_OF_DAY) != otherMatch.date.get(Calendar.HOUR_OF_DAY))
                || (this.date.get(Calendar.MINUTE) != otherMatch.date.get(Calendar.MINUTE))) {
            return false;
        }

        if (this.isHome() != otherMatch.isHome()) {
            return false;
        }

        return true;
    }

    public boolean isHome() {
        return home;
    }
}
