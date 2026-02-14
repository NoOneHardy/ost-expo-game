package ch.no1hardy.ost.infrastructure.persistence.mongo.document;

public record Score(
        String id,
        String username,
        Integer score
) {
}
