package ch.no1hardy.ost.infrastructure.persistence.mongo.service;

import ch.no1hardy.ost.infrastructure.persistence.kurrent.event.ScoreReceived;
import ch.no1hardy.ost.infrastructure.persistence.kurrent.repository.ScoreReceivedRepository;
import ch.no1hardy.ost.infrastructure.persistence.mongo.document.Score;
import ch.no1hardy.ost.infrastructure.persistence.mongo.document.Scoreboard;
import ch.no1hardy.ost.infrastructure.persistence.mongo.repository.ScoreboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SnapshotService {
    private final ScoreboardRepository scoreboardRepository;
    private final ScoreReceivedRepository scoreReceivedRepository;

    public Scoreboard generateLeaderboard() {
        Optional<Scoreboard> latest = getLatestScoreboard();

        Scoreboard scoreboard = new Scoreboard();
        latest.ifPresent(s -> scoreboard.setScores(s.getScores()));
        List<ScoreReceived> lastScores = latest.map(Scoreboard::getLastestId)
                .map(UUID::fromString)
                .map(scoreReceivedRepository::listSince)
                .orElse(scoreReceivedRepository.findAll());
        lastScores.forEach(score -> {
            Score newScore = new Score(score.id().toString(), score.playerName(), score.score());
            scoreboard.addScore(newScore);
        });
        scoreboard.generate();
        return scoreboard;
    }

    public void createSnapshot() {
        Scoreboard scoreboard = generateLeaderboard();
        if (checkForSnapshot()) scoreboardRepository.save(scoreboard);
    }

    private boolean checkForSnapshot() {
        Optional<Scoreboard> latest = getLatestScoreboard();
        if (latest.isEmpty()) return true;
        UUID id = UUID.fromString(latest.get().getLastestId());
        return !scoreReceivedRepository.isEventInLast(id, 20);
    }

    private Optional<Scoreboard> getLatestScoreboard() {
        List<Scoreboard> scores = scoreboardRepository.findAll();
        if (scores.isEmpty()) return Optional.empty();
        return Optional.of(scores.getLast());
    }
}
