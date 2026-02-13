package ch.no1hardy.ost.infrastructure.persistence.jpa.repository;

import ch.no1hardy.ost.infrastructure.persistence.jpa.entity.RaceEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RaceRepository extends CrudRepository<RaceEntity, UUID> {
}
