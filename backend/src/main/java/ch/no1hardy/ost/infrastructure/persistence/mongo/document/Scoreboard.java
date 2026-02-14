package ch.no1hardy.ost.infrastructure.persistence.mongo.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Document
public class Scoreboard {
    private String id;
    private String lastestId;
    private List<Score> scores;

    public Scoreboard() {
        setId(UUID.randomUUID().toString());
    }

    public void addScore(Score score) {
        List<Score> scores = new ArrayList<>();
        if (getScores() != null) {
            scores.addAll(getScores());
        }
        scores.add(score);
        setScores(scores);
        setLastestId(score.id());
    }

    public void generate() {
        if (getScores() == null) setScores(List.of());
        setScores(getScores().stream().sorted(this::scoreComparator).toList());
    }

    private int scoreComparator(Score score1, Score score2) {
        return Long.compare(score1.score(), score2.score());
    }
}
