package de.marcuskrahl.dynamospielplan.test;

import org.junit.Test;
import static org.junit.Assert.*;

import static de.marcuskrahl.dynamospielplan.test.TestHelper.*;

import de.marcuskrahl.dynamospielplan.Match;
import de.marcuskrahl.dynamospielplan.MatchType;
import de.marcuskrahl.dynamospielplan.HtmlMatchParser;

public class HtmlMatchParserTest {

    private final String templateMatch =
            "<tr class=\"test\"> <td>23.06.2015 - Di</td> <td class=\"nb\">18:30 Uhr</td> <td class=\"img test\">" +
            "<span class=\"game\">Testspiele - Sommervorbereitung</span> <span class=\"teams\">Eichsfeld-Auswahl - SG Dynamo Dresden</span> </td>"+
            "<td class=\"result\"><span>0:7 </span></td> </tr>";

    @Test
    public void WhenCalledWithAValidMatch_HtmlMatchParser_ReturnsTheMatch() {
        HtmlMatchParser parser = new HtmlMatchParser();

        Match match = parser.parse(templateMatch);

        assertNotNull(match);
        assertEquals(MatchType.Test, match.getMatchType());
        assertEquals("Eichsfeld-Auswahl", match.getOpponent());
        assertEquals(getLocalDate(2015,6,23,18,30), match.getDate());
    }
}
