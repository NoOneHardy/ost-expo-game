package ch.no1hardy.ost.infrastructure.mapper.race;

import ch.no1hardy.ost.domain.race.event.RaceEndEvent;
import ch.no1hardy.ost.domain.race.model.Race;
import ch.no1hardy.ost.infrastructure.persistence.kurrent.event.ScoreReceived;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;

import java.time.Duration;

@Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface RaceEndEventMapper {
    @Mapping(target = "playerName", source = "event.race.username")
    @Mapping(target = "id", source = "event.race.id")
    @Mapping(target = "score", source = "event.race", qualifiedByName = "mapScore")
    ScoreReceived toScoreReceived(RaceEndEvent event);

    @Named("mapScore")
    default int mapScore(Race event) {
        Duration duration = Duration.between(event.end(), event.start());
        return (int) duration.toMillis();

    }
}
