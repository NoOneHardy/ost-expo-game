package ch.no1hardy.ost.infrastructure.persistence.jpa.port;

import ch.no1hardy.ost.application.race.dto.in.RaceEndDto;
import ch.no1hardy.ost.application.race.port.out.RaceEndRepoPort;
import ch.no1hardy.ost.domain.race.model.Race;
import ch.no1hardy.ost.infrastructure.mapper.race.RaceDtoMapper;
import ch.no1hardy.ost.infrastructure.mapper.race.RaceEntityMapper;
import ch.no1hardy.ost.infrastructure.persistence.jpa.entity.RaceEntity;
import ch.no1hardy.ost.infrastructure.persistence.jpa.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RaceEndRepoImpl implements RaceEndRepoPort {
    private final RaceRepository repository;
    private final RaceEntityMapper entityMapper;
    private final RaceDtoMapper dtoMapper;

    @Override
    public Optional<Race> endRace(RaceEndDto dto, LocalDateTime endTime, UUID id) {
        Optional<RaceEntity> entity = repository.findById(id);
        if (isAlreadyCompleted(entity)) return Optional.empty();
        Optional<Race> race = entity.map(e -> dtoMapper.fromDto(id, dto, e.getStartTime(), endTime));
        return race.map(entityMapper::toEntity)
                .map(repository::save)
                .map(entityMapper::toDomain);
    }

    private boolean isAlreadyCompleted(Optional<RaceEntity> entity) {
        return entity.map(RaceEntity::getEndTime).orElse(null) != null;
    }
}
