package de.marcuskrahl.dynamospielplan;

import java.util.List;

public class MatchPlanComparer {

    private MatchPlanComparisonResult result;
    private MatchPlan oldMatchPlan;
    private MatchPlan newMatchPlan;


    public MatchPlanComparisonResult compare(MatchPlan oldMatchPlan, MatchPlan newMatchPlan) {
        this.result = new MatchPlanComparisonResult();
        this.oldMatchPlan = oldMatchPlan;
        this.newMatchPlan = newMatchPlan;

        addNewMatches();
        deleteObsoleteMatches();

        return result;
    }

    private void addNewMatches() {
        addMatchesNotInSecondPlanToList(newMatchPlan,oldMatchPlan,result.matchesToAdd);
    }

    private void deleteObsoleteMatches() {
        addMatchesNotInSecondPlanToList(oldMatchPlan,newMatchPlan,result.matchesToDelete);
    }

    private void addMatchesNotInSecondPlanToList(MatchPlan firstPlan, MatchPlan secondPlan, List<Match> listToAddTo) {
        for (Match match: firstPlan.matches) {
            if(!planContainsMatch(secondPlan,match)) {
                listToAddTo.add(match);
            }
        }
    }

    private boolean planContainsMatch(MatchPlan plan, Match matchToSearch) {
        for(Match match: plan.matches) {
            if (matchToSearch.equals(match)) {
                return true;
            }
        }
        return false;
    }
}
