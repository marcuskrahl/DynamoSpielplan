package de.marcuskrahl.dynamospielplan;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import de.marcuskrahl.dynamospielplan.exceptions.HtmlParseException;
import de.marcuskrahl.dynamospielplan.exceptions.InvalidMatchTypeException;

public class HtmlMatchParser {

    /*"<tr class=\"test\"> <td>23.06.2015 - Di</td> <td class=\"nb\">18:30 Uhr</td> <td class=\"img test\">" +
            "<span class=\"game\">Testspiele - Sommervorbereitung</span> <span class=\"teams\">Eichsfeld-Auswahl - SG Dynamo Dresden</span> </td>"+
            "<td class=\"result\"><span>0:7 </span></td> </tr>";*/

    private final Pattern matchTypePattern = Pattern.compile("<tr[^>]* class=\"([^\"]*)\"[^>]*>.*");
    private final Pattern opponentPattern = Pattern.compile(".*<span class=\"teams\">([^<]*) - ([^<]*)</span>.*");
    private final Pattern datePattern = Pattern.compile("(?:</span>|<td[^>]*>)\\s*(\\d\\d)\\.(\\d\\d).(\\d\\d\\d\\d)[^<]*</td>");
    private final Pattern timePattern = Pattern.compile("<td[^>]*>(\\d\\d):(\\d\\d)\\s+Uhr[^<]*</td>");

    public Match parse(String matchHtml) throws HtmlParseException {
        MatchType matchType = getMatchType(matchHtml);
        String opponent = getOpponent(matchHtml);
        boolean isHome = isHomeMatch(matchHtml);
        Calendar date = getDate(matchHtml);
        return new Match(matchType,opponent,date,isHome);
    }

    private MatchType getMatchType(String matchHtml) throws HtmlParseException {
        Matcher typeMatch = findPatternInHtml(matchTypePattern,matchHtml);
        String typeString = typeMatch.group(1).toLowerCase();
        if (typeString.contains("test")) {
            return MatchType.Test;
        } else if (typeString.contains("cup")) {
            return MatchType.Cup;
        } else if (typeString.contains("league")) {
            return MatchType.League;
        } else {
            throw new InvalidMatchTypeException(typeString);
        }
    }

    private String getOpponent(String matchHtml) throws HtmlParseException {
        Matcher opponentMatch = findPatternInHtml(opponentPattern,matchHtml);
        if (isHomeMatch(opponentMatch)) {
            return opponentMatch.group(2);
        } else {
            return opponentMatch.group(1);
        }
    }

    private Matcher findPatternInHtml(Pattern pattern, String matchHtml) throws HtmlParseException {
        Matcher matcher = pattern.matcher(matchHtml);
        if (! matcher.find()) {
            throw new HtmlParseException(matchHtml);
        }
        return matcher;
    }

    private boolean isHomeMatch(String matchHtml) throws HtmlParseException {
        Matcher opponentMatch = findPatternInHtml(opponentPattern,matchHtml);
        return isHomeMatch(opponentMatch);
    }

    private boolean isHomeMatch(Matcher opponentMatch) {
        if (opponentMatch.group(1).equals("SG Dynamo Dresden")) {
            return true;
        } else {
            return false;
        }
    }

    private Calendar getDate(String matchHtml) throws HtmlParseException{
        Matcher dateMatch = findPatternInHtml(datePattern,matchHtml);
        int day = Integer.parseInt(dateMatch.group(1));
        int month = Integer.parseInt(dateMatch.group(2));
        int year = Integer.parseInt(dateMatch.group(3));

        Matcher timeMatch = findPatternInHtml(timePattern,matchHtml);
        int hour = Integer.parseInt(timeMatch.group(1));
        int minute = Integer.parseInt(timeMatch.group(2));

        Calendar date = GregorianCalendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"));
        date.set(year,month-1,day,hour,minute,0);
        date.set(date.MILLISECOND,0);
        return date;
    }
}
