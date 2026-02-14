package ch.no1hardy.ost.application.scoreboard.port.out;

import ch.no1hardy.ost.domain.scoreboard.model.Score;

import java.util.List;
import java.util.UUID;

public interface GetScoresSinceRepoPort {
    List<Score> getScoresSince(UUID uuid);
}
