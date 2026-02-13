package ch.no1hardy.ost.infrastructure.mapper.race;

import ch.no1hardy.ost.domain.race.model.Race;
import ch.no1hardy.ost.infrastructure.persistence.jpa.entity.RaceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface RaceEntityMapper {
    @Mapping(target = "startTime", source = "start")
    @Mapping(target = "endTime", source = "end")
    RaceEntity toEntity(Race race);

    @Mapping(target = "start", source = "startTime")
    @Mapping(target = "end", source = "endTime")
    Race toDomain(RaceEntity entity);
}
