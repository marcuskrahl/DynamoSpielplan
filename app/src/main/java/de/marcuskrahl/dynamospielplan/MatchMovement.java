package de.marcuskrahl.dynamospielplan;

import java.util.Calendar;

public class MatchMovement {

    private final Match match;
    private final Calendar newDate;

    public MatchMovement(Match match, Calendar newDate) {
        this.match = match;
        this.newDate = newDate;
    }

    public Match getMatch() {
        return match;
    }

    public Calendar getNewDate() {
        return newDate;
    }
}
