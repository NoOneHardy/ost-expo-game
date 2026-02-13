package ch.no1hardy.ost.infrastructure.persistence.mongo.repository;

import ch.no1hardy.ost.infrastructure.persistence.mongo.document.Scoreboard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ScoreboardRepository extends MongoRepository<Scoreboard, UUID> {
}
