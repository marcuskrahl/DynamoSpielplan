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
}
