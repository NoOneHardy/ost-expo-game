package ch.no1hardy.ost.infrastructure.mapper.race;

import ch.no1hardy.ost.application.race.dto.in.RaceEndDto;
import ch.no1hardy.ost.application.race.dto.out.RaceDto;
import ch.no1hardy.ost.domain.race.model.Race;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface RaceDtoMapper {
    @Mapping(target = "id", expression = "java(id)")
    @Mapping(target = "end", expression = "java(endTime)")
    @Mapping(target = "start", expression = "java(startTime)")
    Race fromDto(UUID id, RaceEndDto dto, LocalDateTime endTime, LocalDateTime startTime);

    RaceDto toDto(Race race);
}
