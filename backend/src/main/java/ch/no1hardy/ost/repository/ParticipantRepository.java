package ch.no1hardy.ost.repository;

import ch.no1hardy.ost.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Participant entity
 */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    
    /**
     * Find a participant by email address
     * @param email the email address to search for
     * @return Optional containing the participant if found
     */
    Optional<Participant> findByEmail(String email);
    
    /**
     * Check if a participant with the given email exists
     * @param email the email address to check
     * @return true if participant exists, false otherwise
     */
    boolean existsByEmail(String email);
}
