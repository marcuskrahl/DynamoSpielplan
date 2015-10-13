package de.marcuskrahl.dynamospielplan;

import java.net.URL;

public class HtmlMatchPlanRetriever {

    private URL url;

    public HtmlMatchPlanRetriever(URL url) {
        if (url == null) {
            throw new NullPointerException();
        }
        this.url = url;
    }

    public MatchPlan retrieve() {
        return new MatchPlan();
    }
}
