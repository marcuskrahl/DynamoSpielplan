package de.marcuskrahl.dynamospielplan.test;

import org.junit.*;

import de.marcuskrahl.dynamospielplan.HtmlMatchPlanRetriever;
import de.marcuskrahl.dynamospielplan.MatchPlan;

import static org.junit.Assert.*;

public class HtmlMatchPlanRetrieverTest {

    @Test
    public void WhenCalled_HtmlMatchPlanRetriever_ReturnsMatchPlan() {
        HtmlMatchPlanRetriever retriever = new HtmlMatchPlanRetriever();

        MatchPlan plan = retriever.retrieve();

        assertNotNull(plan);
        assertTrue(plan instanceof MatchPlan);
    }
}
