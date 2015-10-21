package de.marcuskrahl.dynamospielplan;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class HtmlMatchParser {

    /*"<tr class=\"test\"> <td>23.06.2015 - Di</td> <td class=\"nb\">18:30 Uhr</td> <td class=\"img test\">" +
            "<span class=\"game\">Testspiele - Sommervorbereitung</span> <span class=\"teams\">Eichsfeld-Auswahl - SG Dynamo Dresden</span> </td>"+
            "<td class=\"result\"><span>0:7 </span></td> </tr>";*/

    private final Pattern matchTypePattern = Pattern.compile("<tr[^>]* class=\"([^\"]*)\"[^>]*>.*");
    private final Pattern opponentPattern = Pattern.compile(".*<span class=\"teams\">([^<]*) - ([^<]*)</span>.*");
    private final Pattern datePattern = Pattern.compile("<td[^>]*>\\s*(\\d\\d)\\.(\\d\\d).(\\d\\d\\d\\d)[^<]*</td>");
    private final Pattern timePattern = Pattern.compile("<td[^>]*>(\\d\\d):(\\d\\d)\\s+Uhr[^<]*</td>");

    public Match parse(String matchHtml) {
        MatchType matchType = getMatchType(matchHtml);
        String opponent = getOpponent(matchHtml);
        Calendar date = getDate(matchHtml);
        return new Match(matchType,opponent,date);
    }

    private MatchType getMatchType(String matchHtml) {
        Matcher typeMatch = matchTypePattern.matcher(matchHtml);
        typeMatch.find();
        String typeString = typeMatch.group(1).toLowerCase();
        if (typeString.contains("test")) {
            return MatchType.Test;
        } else if (typeString.contains("cup")) {
            return MatchType.Cup;
        } else if (typeString.contains("league")) {
            return MatchType.League;
        } else {
            throw new Error("Not implemented");
        }
    }

    private String getOpponent(String matchHtml) {
        Matcher opponentMatch = opponentPattern.matcher(matchHtml);
        opponentMatch.find();
        String opponentString = opponentMatch.group(1);
        if (opponentString.equals("SG Dynamo Dresden")) {
            opponentString = opponentMatch.group(2);
        }
        return opponentString;
    }

    private Calendar getDate(String matchHtml) {
        Matcher dateMatch = datePattern.matcher(matchHtml);
        dateMatch.find();
        int day = Integer.parseInt(dateMatch.group(1));
        int month = Integer.parseInt(dateMatch.group(2));
        int year = Integer.parseInt(dateMatch.group(3));

        Matcher timeMatch = timePattern.matcher(matchHtml);
        timeMatch.find();
        int hour = Integer.parseInt(timeMatch.group(1));
        int minute = Integer.parseInt(timeMatch.group(2));

        Calendar date = GregorianCalendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"));
        date.set(year,month-1,day,hour,minute,0);
        date.set(date.MILLISECOND,0);
        return date;
    }
}
