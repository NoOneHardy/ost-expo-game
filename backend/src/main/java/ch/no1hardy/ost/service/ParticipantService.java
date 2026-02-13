package ch.no1hardy.ost.service;

import ch.no1hardy.ost.domain.Participant;
import ch.no1hardy.ost.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing participants
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantService {
    
    private final ParticipantRepository participantRepository;
    
    /**
     * Save a new participant or update an existing one
     * @param participant the participant to save
     * @return the saved participant
     */
    @Transactional
    public Participant saveParticipant(Participant participant) {
        log.info("Saving participant with email: {}", participant.getEmail());
        return participantRepository.save(participant);
    }
    
    /**
     * Find a participant by email
     * @param email the email to search for
     * @return Optional containing the participant if found
     */
    public Optional<Participant> findByEmail(String email) {
        log.debug("Finding participant by email: {}", email);
        return participantRepository.findByEmail(email);
    }
    
    /**
     * Check if a participant with the given email exists
     * @param email the email to check
     * @return true if participant exists, false otherwise
     */
    public boolean existsByEmail(String email) {
        return participantRepository.existsByEmail(email);
    }
    
    /**
     * Get all participants
     * @return list of all participants
     */
    public List<Participant> getAllParticipants() {
        log.debug("Retrieving all participants");
        return participantRepository.findAll();
    }
    
    /**
     * Get a participant by ID
     * @param id the participant ID
     * @return Optional containing the participant if found
     */
    public Optional<Participant> getParticipantById(Long id) {
        log.debug("Finding participant by id: {}", id);
        return participantRepository.findById(id);
    }
}
