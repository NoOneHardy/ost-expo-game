package ch.no1hardy.ost.infrastructure.persistence.mongo.repository;

import ch.no1hardy.ost.infrastructure.persistence.mongo.document.RaceDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RaceRepository extends MongoRepository<RaceDocument, UUID> {
}
