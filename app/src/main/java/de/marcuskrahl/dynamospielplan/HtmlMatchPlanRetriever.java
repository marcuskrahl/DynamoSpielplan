package de.marcuskrahl.dynamospielplan;

import java.util.GregorianCalendar;

public class HtmlMatchPlanRetriever {

    private MatchPlanURL url;

    public HtmlMatchPlanRetriever(MatchPlanURL url) {
        if (url == null) {
            throw new NullPointerException();
        }
        this.url = url;
    }

    public MatchPlan retrieve() {

        return new MatchPlan(new Match[] {
                new Match(MatchType.League, "BFC", GregorianCalendar.getInstance()),
                new Match(MatchType.League, "BFC", GregorianCalendar.getInstance()),
                new Match(MatchType.League, "BFC", GregorianCalendar.getInstance())
        });
    }
}
