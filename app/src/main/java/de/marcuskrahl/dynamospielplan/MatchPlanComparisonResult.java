package de.marcuskrahl.dynamospielplan;

import java.util.List;
import java.util.ArrayList;

public class MatchPlanComparisonResult {

    public final List<Match> matchesToAdd;
    public final List<Match> matchesToDelete;
    public final List<MatchMovement> matchMovements;

    public MatchPlanComparisonResult() {
        matchesToAdd = new ArrayList<Match>();
        matchesToDelete = new ArrayList<Match>();
        matchMovements = new ArrayList<MatchMovement>();
    }
}
