package de.marcuskrahl.dynamospielplan.test;

import org.junit.*;

import de.marcuskrahl.dynamospielplan.HtmlMatchPlanRetriever;
import de.marcuskrahl.dynamospielplan.MatchPlan;
import de.marcuskrahl.dynamospielplan.MatchPlanURL;

import static org.junit.Assert.*;

public class HtmlMatchPlanRetrieverTest {

    @Test(expected = NullPointerException.class)
    public void WhenCalledWithoutURL_HtmlMatchPlanRetriever_ThrowsNullPointerException() {
        HtmlMatchPlanRetriever retriever = new HtmlMatchPlanRetriever(null);
    }

    @Test
    public void WhenCalled_HtmlMatchPlanRetriever_ReturnsMatchPlan() {
        HtmlMatchPlanRetriever retriever = new HtmlMatchPlanRetriever(new DummyMatchPlanURL());

        MatchPlan plan = retriever.retrieve();

        assertNotNull(plan);
        assertTrue(plan instanceof MatchPlan);
    }

    private class DummyMatchPlanURL implements MatchPlanURL {

        public String getContent() {
            return "";
        }
    }
}
