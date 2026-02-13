package ch.no1hardy.ost.mapper;

import ch.no1hardy.ost.domain.Participant;
import ch.no1hardy.ost.dto.ParticipantRequest;
import ch.no1hardy.ost.dto.ParticipantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for Participant entity and DTOs
 */
@Mapper(componentModel = "spring")
public interface ParticipantMapper {
    
    /**
     * Convert ParticipantRequest DTO to Participant entity
     * @param request the request DTO
     * @return Participant entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "problemsHappened", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Participant toEntity(ParticipantRequest request);
    
    /**
     * Convert Participant entity to ParticipantResponse DTO
     * @param participant the entity
     * @return ParticipantResponse DTO
     */
    ParticipantResponse toResponse(Participant participant);
}
