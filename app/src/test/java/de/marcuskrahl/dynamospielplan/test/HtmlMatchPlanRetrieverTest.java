package de.marcuskrahl.dynamospielplan.test;

import org.junit.*;

import de.marcuskrahl.dynamospielplan.HtmlMatchPlanRetriever;
import de.marcuskrahl.dynamospielplan.MatchPlan;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class HtmlMatchPlanRetrieverTest {

    @Test(expected = NullPointerException.class)
    public void WhenCalledWithoutURL_HtmlMatchPlanRetriever_ThrowsNullPointerException() {
        HtmlMatchPlanRetriever retriever = new HtmlMatchPlanRetriever(null);
    }

    @Test
    public void WhenCalled_HtmlMatchPlanRetriever_ReturnsMatchPlan() throws MalformedURLException {
        HtmlMatchPlanRetriever retriever = new HtmlMatchPlanRetriever(new URL("http://www.example.com"));

        MatchPlan plan = retriever.retrieve();

        assertNotNull(plan);
        assertTrue(plan instanceof MatchPlan);
    }
}
