package de.marcuskrahl.dynamospielplan.test;

import org.junit.*;

import de.marcuskrahl.dynamospielplan.HtmlMatchPlanRetriever;
import de.marcuskrahl.dynamospielplan.MatchPlan;
import de.marcuskrahl.dynamospielplan.MatchPlanURL;

import static org.junit.Assert.*;

public class HtmlMatchPlanRetrieverTest {

    private final String threeMatchesHTML = "";

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

    @Test
    public void WhenCalled_HtmlMatchPlanRetriever_ReturnsCorrectNumberOfMatches() {
        HtmlMatchPlanRetriever retriever = new HtmlMatchPlanRetriever(new DummyMatchPlanURL(threeMatchesHTML));

        MatchPlan plan = retriever.retrieve();

        assertEquals(plan.matches.length,3);
    }

    private class DummyMatchPlanURL implements MatchPlanURL {

        private final String stringToReturn;

        public DummyMatchPlanURL() {
            this("");
        }

        public DummyMatchPlanURL(String stringToReturn) {
            this.stringToReturn = stringToReturn;
        }

        public String getContent() {
            return stringToReturn;
        }
    }
}
