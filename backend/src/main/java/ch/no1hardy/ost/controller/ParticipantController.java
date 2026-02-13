package ch.no1hardy.ost.controller;

import ch.no1hardy.ost.domain.Participant;
import ch.no1hardy.ost.dto.ParticipantRequest;
import ch.no1hardy.ost.dto.ParticipantResponse;
import ch.no1hardy.ost.mapper.ParticipantMapper;
import ch.no1hardy.ost.service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for managing participants
 */
@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
@Slf4j
public class ParticipantController {
    
    private final ParticipantService participantService;
    private final ParticipantMapper participantMapper;
    
    /**
     * Register a new participant with email
     * @param request the participant registration request
     * @return the created participant
     */
    @PostMapping
    public ResponseEntity<ParticipantResponse> registerParticipant(@Valid @RequestBody ParticipantRequest request) {
        log.info("Received registration request for email: {}", request.getEmail());
        
        // Check if participant already exists
        if (participantService.existsByEmail(request.getEmail())) {
            log.warn("Participant with email {} already exists", request.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        // Convert DTO to entity, save, and convert back to DTO
        Participant participant = participantMapper.toEntity(request);
        Participant savedParticipant = participantService.saveParticipant(participant);
        ParticipantResponse response = participantMapper.toResponse(savedParticipant);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Get all participants
     * @return list of all participants
     */
    @GetMapping
    public ResponseEntity<List<ParticipantResponse>> getAllParticipants() {
        log.info("Retrieving all participants");
        List<ParticipantResponse> participants = participantService.getAllParticipants()
                .stream()
                .map(participantMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(participants);
    }
    
    /**
     * Get a participant by ID
     * @param id the participant ID
     * @return the participant if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParticipantResponse> getParticipantById(@PathVariable Long id) {
        log.info("Retrieving participant with id: {}", id);
        return participantService.getParticipantById(id)
                .map(participantMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get a participant by email
     * @param email the participant email
     * @return the participant if found
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<ParticipantResponse> getParticipantByEmail(@PathVariable String email) {
        log.info("Retrieving participant with email: {}", email);
        return participantService.findByEmail(email)
                .map(participantMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
