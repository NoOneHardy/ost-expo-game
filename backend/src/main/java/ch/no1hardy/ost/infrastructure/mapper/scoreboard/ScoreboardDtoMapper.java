package ch.no1hardy.ost.infrastructure.mapper.scoreboard;

import ch.no1hardy.ost.application.scoreboard.dto.out.ScoreboardDto;
import ch.no1hardy.ost.domain.scoreboard.model.Scoreboard;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface ScoreboardDtoMapper {
    ScoreboardDto toDto(Scoreboard domain);
}
