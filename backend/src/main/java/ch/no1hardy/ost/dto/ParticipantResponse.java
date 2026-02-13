package ch.no1hardy.ost.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for participant response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantResponse {
    
    private Long id;
    private String email;
    private String riskDecision;
    private Boolean problemsHappened;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
}
