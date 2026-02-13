package ch.no1hardy.ost.infrastructure.persistence.jpa.repository;

import ch.no1hardy.ost.infrastructure.persistence.jpa.entity.RaceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface RaceRepository extends CrudRepository<RaceEntity, UUID> {
    @Query("SELECT r FROM RaceEntity r WHERE r.endTime IS NULL")
    List<RaceEntity> findWhereEndTimeIsNull();
}
