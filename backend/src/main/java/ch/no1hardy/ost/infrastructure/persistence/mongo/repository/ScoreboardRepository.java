package ch.no1hardy.ost.infrastructure.persistence.mongo.repository;

import ch.no1hardy.ost.infrastructure.persistence.mongo.document.ScoreboardDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ScoreboardRepository extends MongoRepository<ScoreboardDocument, UUID> {
}
