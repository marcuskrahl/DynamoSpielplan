package de.marcuskrahl.dynamospielplan;

public class HtmlMatchPlanRetriever {

    private MatchPlanURL url;

    public HtmlMatchPlanRetriever(MatchPlanURL url) {
        if (url == null) {
            throw new NullPointerException();
        }
        this.url = url;
    }

    public MatchPlan retrieve() {
        return new MatchPlan(new Match[3]);
    }
}
