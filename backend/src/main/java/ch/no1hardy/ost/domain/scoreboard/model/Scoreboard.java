package ch.no1hardy.ost.domain.scoreboard.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public record Scoreboard(
        UUID id,
        UUID latestId,
        List<Score> scores
) {
    public Scoreboard addScores(List<Score> scores) {
        List<Score> newScores = removeScores(scores).scores();
        newScores.addAll(scores);
        return new Scoreboard(id, getLatestIdOrDefault(scores), newScores);
    }

    public Scoreboard removeScores(List<Score> scores) {
        List<Score> newScores = getMutableScores();
        newScores.removeAll(scores);
        return new Scoreboard(id, getLatestIdOrDefault(scores), newScores);
    }

    public Scoreboard sortScores() {
        List<Score> sortedScores = getMutableScores();
        sortedScores.sort(Comparator.comparingInt(Score::score));
        return new Scoreboard(id, latestId, sortedScores);
    }

    private UUID getLatestIdOrDefault(List<Score> scores) {
        if (scores.isEmpty()) return latestId;
        return scores.getLast().id();
    }

    private List<Score> getMutableScores() {
        return new ArrayList<>(scores);
    }
}
