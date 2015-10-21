package de.marcuskrahl.dynamospielplan.test;

import org.junit.Test;
import static org.junit.Assert.*;

import static de.marcuskrahl.dynamospielplan.test.TestHelper.*;

import de.marcuskrahl.dynamospielplan.Match;
import de.marcuskrahl.dynamospielplan.MatchType;
import de.marcuskrahl.dynamospielplan.HtmlMatchParser;
import de.marcuskrahl.dynamospielplan.exceptions.HtmlParseException;
import de.marcuskrahl.dynamospielplan.exceptions.InvalidMatchTypeException;

public class HtmlMatchParserTest {

    private final String templateMatch =
                    "<tr class=\"test\"> <td>23.06.2015 - Di</td> <td class=\"nb\">18:30 Uhr</td> <td class=\"img test\">" +
                    "<span class=\"game\">Testspiele - Sommervorbereitung</span> <span class=\"teams\">Eichsfeld-Auswahl - SG Dynamo Dresden</span> </td>"+
                    "<td class=\"result\"><span>0:7 </span></td> </tr>";

    private final String templateMatch2 =
                    "<tr class=\"league3 active tr-link\"> <td>27.09.2015 - So</td> <td class=\"nb\">14:00 Uhr</td> <td class=\"img 14:00 Uhr\">" +
                    "<a href=\"saison/spielplan/2015-2016/spielbericht/spiel/9669.html\">" +
                    "<span class=\"game\">3. Liga - 11. Spieltag</span> <span class=\"teams\">SG Dynamo Dresden - VfR Aalen</span></a></td>"+
                    "<td class=\"result\"><span><a href=\"ssaison/spielplan/2015-2016/spielbericht/spiel/9669.html\">4:0 (2:0) </a></span></td> </tr>";

    private final String templateMatch3 =
                    "<tr class=\"cup-region active tr-link\"> <td>09.10.2015 - Fr</td> <td class=\"nb\">19:00 Uhr</td> <td class=\"img cup-region\">" +
                    "<a href=\"saison/spielplan/2015-2016/spielbericht/spiel/11303.html\">" +
                    "<span class=\"game\">Sachsen-Pokal - Achtelfinale</span> <span class=\"teams\">SG Dynamo Dresden - Chemnitzer FC</span></a></td>"+
                    "<td class=\"result\"><span><a href=\"saison/spielplan/2015-2016/spielbericht/spiel/11303.html\">2:1 (1:1) </a></span></td> </tr>";

    private final String templateMatchUnknownMatchType =
                    "<tr class=\"unknown\"> <td>23.06.2015 - Di</td> <td class=\"nb\">18:30 Uhr</td> <td class=\"img test\">" +
                    "<span class=\"game\">Testspiele - Sommervorbereitung</span> <span class=\"teams\">Eichsfeld-Auswahl - SG Dynamo Dresden</span> </td>"+
                    "<td class=\"result\"><span>0:7 </span></td> </tr>";

    private final String templateMatchInvalidType=
                    "<tr> <td>23.06.2015 - Di</td> <td class=\"nb\">18:30 Uhr</td> <td class=\"img test\">" +
                    "<span class=\"game\">Testspiele - Sommervorbereitung</span> <span class=\"teams\">Eichsfeld-Auswahl - SG Dynamo Dresden</span> </td>"+
                    "<td class=\"result\"><span>0:7 </span></td> </tr>";

    private final String templateMatchInvalidOpponent =
                    "<tr class=\"test\"> <td>23.06.2015 - Di</td> <td class=\"nb\">18:30 Uhr</td> <td class=\"img test\">" +
                    "<span class=\"game\">Testspiele - Sommervorbereitung</span> <span class=\"teams\">Eichsfeld-Auswahl gegen SG Dynamo Dresden</span> </td>"+
                    "<td class=\"result\"><span>0:7 </span></td> </tr>";
    private final String templateMatchInvalidDate =
                    "<tr class=\"test\"> <td>23/06/2015 - Di</td> <td class=\"nb\">18:30 Uhr</td> <td class=\"img test\">" +
                    "<span class=\"game\">Testspiele - Sommervorbereitung</span> <span class=\"teams\">Eichsfeld-Auswahl - SG Dynamo Dresden</span> </td>"+
                    "<td class=\"result\"><span>0:7 </span></td> </tr>";
    private final String templateMatchInvalidTime=
                    "<tr class=\"test\"> <td>23.06.2015 - Di</td> <td class=\"nb\">19:0 Uhr</td> <td class=\"img test\">" +
                    "<span class=\"game\">Testspiele - Sommervorbereitung</span> <span class=\"teams\">Eichsfeld-Auswahl - SG Dynamo Dresden</span> </td>"+
                    "<td class=\"result\"><span>0:7 </span></td> </tr>";

    @Test
    public void WhenCalledWithAValidMatch_HtmlMatchParser_ReturnsTheMatch() throws HtmlParseException {
        HtmlMatchParser parser = new HtmlMatchParser();

        Match match = parser.parse(templateMatch);

        assertEquals(MatchType.Test, match.getMatchType());
        assertEquals("Eichsfeld-Auswahl", match.getOpponent());
        assertEquals(getLocalDate(2015,6,23,18,30), match.getDate());
    }

    @Test
    public void WhenCalledWithAValidMatch2_HtmlMatchParser_ReturnsTheMatch() throws HtmlParseException {
        HtmlMatchParser parser = new HtmlMatchParser();

        Match match = parser.parse(templateMatch2);

        assertEquals(MatchType.League, match.getMatchType());
        assertEquals("VfR Aalen", match.getOpponent());
        assertEquals(getLocalDate(2015,9,27,14,0), match.getDate());
    }

    @Test
    public void WhenCalledWithAValidMatch3_HtmlMatchParser_ReturnsTheMatch() throws HtmlParseException {
        HtmlMatchParser parser = new HtmlMatchParser();

        Match match = parser.parse(templateMatch3);

        assertEquals(MatchType.Cup, match.getMatchType());
        assertEquals("Chemnitzer FC", match.getOpponent());
        assertEquals(getLocalDate(2015,10,9,19,0), match.getDate());
    }

    @Test(expected = InvalidMatchTypeException.class)
    public void WhenCalledWithAnUnknownMatchType_HtmlMatchParser_ThrowsInvalidMatchTypeException() throws HtmlParseException {
        HtmlMatchParser parser = new HtmlMatchParser();

        Match match = parser.parse(templateMatchUnknownMatchType);
    }

    @Test(expected = HtmlParseException.class)
    public void WhenCalledWithInvalidTypeHtml_HtmlMatchParser_ThrowsHtmlParseException() throws HtmlParseException {
        HtmlMatchParser parser = new HtmlMatchParser();

        Match match = parser.parse(templateMatchInvalidType);
    }

    @Test(expected = HtmlParseException.class)
    public void WhenCalledWithInvalidOpponentHtml_HtmlMatchParser_ThrowsHtmlParseException() throws HtmlParseException {
        HtmlMatchParser parser = new HtmlMatchParser();

        Match match = parser.parse(templateMatchInvalidOpponent);
    }

    @Test(expected = HtmlParseException.class)
    public void WhenCalledWithInvalidDateHtml_HtmlMatchParser_ThrowsHtmlParseException() throws HtmlParseException {
        HtmlMatchParser parser = new HtmlMatchParser();

        Match match = parser.parse(templateMatchInvalidDate);
    }

    @Test(expected = HtmlParseException.class)
    public void WhenCalledWithInvalidTimeHtml_HtmlMatchParser_ThrowsHtmlParseException() throws HtmlParseException {
        HtmlMatchParser parser = new HtmlMatchParser();

        Match match = parser.parse(templateMatchInvalidTime);
    }

}
