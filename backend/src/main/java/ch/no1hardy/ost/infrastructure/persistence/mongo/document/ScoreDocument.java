package ch.no1hardy.ost.infrastructure.persistence.mongo.document;

public record ScoreDocument(
        String id,
        String username,
        Integer score
) {
}
