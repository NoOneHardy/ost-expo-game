package ch.no1hardy.ost.infrastructure.mapper.scoreboard;

import ch.no1hardy.ost.domain.scoreboard.model.Score;
import ch.no1hardy.ost.infrastructure.persistence.kurrent.event.ScoreReceived;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface ScoreEventMapper {
    @Mapping(target = "name", source = "playerName")
    Score toDomain(ScoreReceived event);
}
