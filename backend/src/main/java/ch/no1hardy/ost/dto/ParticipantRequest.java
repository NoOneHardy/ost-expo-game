package ch.no1hardy.ost.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO for creating/registering a new participant
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantRequest {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    private String riskDecision;
}
