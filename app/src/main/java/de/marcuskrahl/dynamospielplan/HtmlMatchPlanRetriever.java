package de.marcuskrahl.dynamospielplan;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import de.marcuskrahl.dynamospielplan.exceptions.TableNotFoundException;

public class HtmlMatchPlanRetriever {

    private final MatchPlanURL url;
    private final Pattern findTablePattern = Pattern.compile("<table[^>]*class=\"[^\"]*bb[^\"]*\"[^>]*>(.+?)</table>");
    private final Pattern findTableBody = Pattern.compile("<tbody>(.*)</tbody>");
    private final Pattern findTableEntries = Pattern.compile("<tr.+?</tr>");

    public HtmlMatchPlanRetriever(MatchPlanURL url) {
        if (url == null) {
            throw new NullPointerException();
        }
        this.url = url;
    }

    public MatchPlan retrieve() throws TableNotFoundException {
        ArrayList<Match> matchPlan = new ArrayList<Match>();
        String pageContent = url.getContent();
        String matchTable = getMatchTable(pageContent);
        String[] matchEntries = getMatchEntries(matchTable);
        for (String matchEntry: matchEntries) {
            matchPlan.add(getMatchByEntry(matchEntry));
        }
        Match[] matchPlanArray = new Match[matchPlan.size()];
        matchPlan.toArray(matchPlanArray);
        return new MatchPlan(matchPlanArray);
    }

    private String getMatchTable(String pageContent) throws TableNotFoundException {
        Matcher tableMatch = findTablePattern.matcher(pageContent);
        if (tableMatch.find()) {
            return tableMatch.group(1);
        } else {
            throw new TableNotFoundException();
        }
    }

    private String[] getMatchEntries(String matchTable) {
        Matcher tableBodyMatch = findTableBody.matcher(matchTable);
        String tableBody;
        if (tableBodyMatch.find()) {
            tableBody = tableBodyMatch.group(1);
        } else {
            return new String[0];
        }

        Matcher tableEntriesMatch = findTableEntries.matcher(tableBody);
        ArrayList<String> tableEntries = new ArrayList<String>();
        while (tableEntriesMatch.find()) {
            tableEntries.add(tableEntriesMatch.group());
        }
        String[] tableEntriesArray = new String[tableEntries.size()];
        tableEntries.toArray(tableEntriesArray);
        return tableEntriesArray;
    }

    private Match getMatchByEntry(String matchEntry) {
        return new Match(MatchType.Cup,"BFC", Calendar.getInstance());
    }
}
