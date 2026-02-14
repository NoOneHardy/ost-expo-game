package ch.no1hardy.ost.application.scoreboard.port.out;

import ch.no1hardy.ost.domain.scoreboard.model.Score;

import java.util.List;

public interface GetNewScoresRepoPort {
    List<Score> getNewScores();
}
