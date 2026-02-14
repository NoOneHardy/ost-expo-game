package ch.no1hardy.ost.infrastructure.mapper.scoreboard;

import ch.no1hardy.ost.domain.scoreboard.model.Score;
import ch.no1hardy.ost.domain.scoreboard.model.Scoreboard;
import ch.no1hardy.ost.infrastructure.persistence.mongo.document.ScoreDocument;
import ch.no1hardy.ost.infrastructure.persistence.mongo.document.ScoreboardDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface ScoreboardDocumentMapper {
    ScoreboardDocument toDocument(Scoreboard domain);

    Scoreboard toDomain(ScoreboardDocument entity);

    @Mapping(target = "name", source = "username")
    Score toDomain(ScoreDocument entity);

    @Mapping(target = "username", source = "name")
    ScoreDocument toDocument(Score domain);

    default UUID toUuid(String value) {
        return UUID.fromString(value);
    }
}
