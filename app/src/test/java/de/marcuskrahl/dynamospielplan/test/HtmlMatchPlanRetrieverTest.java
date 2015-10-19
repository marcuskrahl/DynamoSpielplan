package de.marcuskrahl.dynamospielplan.test;

import org.junit.*;

import static de.marcuskrahl.dynamospielplan.test.TestHelper.*;

import de.marcuskrahl.dynamospielplan.HtmlMatchPlanRetriever;
import de.marcuskrahl.dynamospielplan.MatchPlan;
import de.marcuskrahl.dynamospielplan.MatchPlanURL;
import de.marcuskrahl.dynamospielplan.Match;
import de.marcuskrahl.dynamospielplan.MatchType;
import de.marcuskrahl.dynamospielplan.exceptions.TableNotFoundException;

import static org.junit.Assert.*;

public class HtmlMatchPlanRetrieverTest {

    private final String threeMatchesHTML = "<table class=\"bb\">" +
            "<thead> <tr> <th class=\"match-date\">Datum</th> <th class=\"match-time\">Uhrzeit</th>" +
            "<th class=\"match\">Begegnung (Heim - Gast)</th> <th class=\"result\">Ergebnis</th> </tr> </thead>"+
            "<tbody>"+

            "<tr class=\"test\"> <td>23.06.2015 - Di</td> <td class=\"nb\">18:30 Uhr</td> <td class=\"img test\">" +
            "<span class=\"game\">Testspiele - Sommervorbereitung</span> <span class=\"teams\">Eichsfeld-Auswahl - SG Dynamo Dresden</span> </td>"+
            "<td class=\"result\"><span>0:7 </span></td> </tr>"+

            "<tr class=\"league3 active tr-link\"> <td>27.09.2015 - So</td> <td class=\"nb\">14:00 Uhr</td> <td class=\"img 14:00 Uhr\">" +
            "<a href=\"saison/spielplan/2015-2016/spielbericht/spiel/9669.html\">" +
            "<span class=\"game\">3. Liga - 11. Spieltag</span> <span class=\"teams\">SG Dynamo Dresden - VfR Aalen</span></a></td>"+
            "<td class=\"result\"><span><a href=\"ssaison/spielplan/2015-2016/spielbericht/spiel/9669.html\">4:0 (2:0) </a></span></td> </tr>"+

            "<tr class=\"cup-region active tr-link\"> <td>09.10.2015 - Fr</td> <td class=\"nb\">19:00 Uhr</td> <td class=\"img cup-region\">" +
            "<a href=\"saison/spielplan/2015-2016/spielbericht/spiel/11303.html\">" +
            "<span class=\"game\">Sachsen-Pokal - Achtelfinale</span> <span class=\"teams\">SG Dynamo Dresden - Chemnitzer FC</span></a></td>"+
            "<td class=\"result\"><span><a href=\"saison/spielplan/2015-2016/spielbericht/spiel/11303.html\">2:1 (1:1) </a></span></td> </tr>"+

            "</tbody> </table>";

    @Test(expected = NullPointerException.class)
    public void WhenCalledWithoutURL_HtmlMatchPlanRetriever_ThrowsNullPointerException() {
        HtmlMatchPlanRetriever retriever = new HtmlMatchPlanRetriever(null);
    }

    @Test
    public void WhenCalled_HtmlMatchPlanRetriever_ReturnsMatchPlan() throws Exception {
        HtmlMatchPlanRetriever retriever = new HtmlMatchPlanRetriever(new DummyMatchPlanURL(threeMatchesHTML));

        MatchPlan plan = retriever.retrieve();

        assertNotNull(plan);
        assertTrue(plan instanceof MatchPlan);
    }

    @Test
    public void WhenCalled_HtmlMatchPlanRetriever_ReturnsCorrectNumberOfMatches() throws Exception {
        HtmlMatchPlanRetriever retriever = new HtmlMatchPlanRetriever(new DummyMatchPlanURL(threeMatchesHTML));

        MatchPlan plan = retriever.retrieve();

        assertEquals(3, plan.matches.length);
    }

    @Test
    public void WhenCalled_HtmlMatchPlanRetriever_ReturnsCorrectMatches() throws Exception {
        HtmlMatchPlanRetriever retriever = new HtmlMatchPlanRetriever(new DummyMatchPlanURL(threeMatchesHTML));

        MatchPlan plan = retriever.retrieve();

        assertEquals(new Match(MatchType.Test,"Eichsfeld-Auswahl",getLocalDate(2015,6,23,18,30)), plan.matches[0]);
        assertEquals(new Match(MatchType.League,"VfR Aalen",getLocalDate(2015,9,27,14,0)), plan.matches[1]);
        assertEquals(new Match(MatchType.Cup,"Chemnitzer FC",getLocalDate(2015,10,9,19,0)), plan.matches[2]);
    }

    @Test(expected=TableNotFoundException.class)
    public void WhenNoTablePresent_HtmlMatchPlanRetriever_ThrowsTableNotFoundException() throws TableNotFoundException {
        HtmlMatchPlanRetriever retriever = new HtmlMatchPlanRetriever(new DummyMatchPlanURL("<table>123</table ending missing"));

        MatchPlan plan = retriever.retrieve();
    }

    @Test
    public void WhenTableTagHasDifferentFormat_HtmlMatchPlanRetriever_DoesNotThrow() throws TableNotFoundException {
        HtmlMatchPlanRetriever retriever = new HtmlMatchPlanRetriever(new DummyMatchPlanURL("<table other-attribute=\"123141\" class=\"other-class bb second-other-class\" next-attribute>123</table>"));

        MatchPlan plan = retriever.retrieve();

        assertNotNull(plan);
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
