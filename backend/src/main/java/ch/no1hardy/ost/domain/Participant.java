package ch.no1hardy.ost.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * JPA Entity representing a participant in a race
 */
@Entity
@Table(name = "participants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Email address of the participant
     */
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    /**
     * Risk decision made by the participant (low or high)
     */
    @Column(name = "risk_decision")
    private String riskDecision;
    
    /**
     * Flag indicating whether problems occurred during the participant's race
     */
    @Column(name = "problems_happened")
    private Boolean problemsHappened;
    
    /**
     * Timestamp when the participant finished their race
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    /**
     * Timestamp when the participant was created
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
