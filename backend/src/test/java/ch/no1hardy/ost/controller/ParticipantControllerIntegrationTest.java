package ch.no1hardy.ost.controller;

import ch.no1hardy.ost.domain.Participant;
import ch.no1hardy.ost.dto.ParticipantRequest;
import ch.no1hardy.ost.repository.ParticipantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ParticipantControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ParticipantRepository participantRepository;
    
    @BeforeEach
    void setUp() {
        participantRepository.deleteAll();
    }
    
    @Test
    void registerParticipant_ShouldCreateNewParticipant() throws Exception {
        // Arrange
        ParticipantRequest request = new ParticipantRequest();
        request.setEmail("newuser@example.com");
        request.setRiskDecision("high");
        
        // Act & Assert
        mockMvc.perform(post("/api/participants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("newuser@example.com"))
                .andExpect(jsonPath("$.riskDecision").value("high"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists());
        
        // Verify in database
        assertThat(participantRepository.findByEmail("newuser@example.com")).isPresent();
    }
    
    @Test
    void registerParticipant_ShouldReturnConflict_WhenEmailExists() throws Exception {
        // Arrange - create existing participant
        Participant existing = new Participant();
        existing.setEmail("existing@example.com");
        existing.setRiskDecision("low");
        participantRepository.save(existing);
        
        ParticipantRequest request = new ParticipantRequest();
        request.setEmail("existing@example.com");
        request.setRiskDecision("high");
        
        // Act & Assert
        mockMvc.perform(post("/api/participants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }
    
    @Test
    void registerParticipant_ShouldReturnBadRequest_WhenEmailIsInvalid() throws Exception {
        // Arrange
        ParticipantRequest request = new ParticipantRequest();
        request.setEmail("invalid-email");
        request.setRiskDecision("low");
        
        // Act & Assert
        mockMvc.perform(post("/api/participants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void registerParticipant_ShouldReturnBadRequest_WhenEmailIsEmpty() throws Exception {
        // Arrange
        ParticipantRequest request = new ParticipantRequest();
        request.setEmail("");
        request.setRiskDecision("low");
        
        // Act & Assert
        mockMvc.perform(post("/api/participants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void getAllParticipants_ShouldReturnAllParticipants() throws Exception {
        // Arrange
        Participant p1 = new Participant();
        p1.setEmail("user1@example.com");
        p1.setRiskDecision("low");
        participantRepository.save(p1);
        
        Participant p2 = new Participant();
        p2.setEmail("user2@example.com");
        p2.setRiskDecision("high");
        participantRepository.save(p2);
        
        // Act & Assert
        mockMvc.perform(get("/api/participants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].email").exists())
                .andExpect(jsonPath("$[1].email").exists());
    }
    
    @Test
    void getParticipantById_ShouldReturnParticipant_WhenExists() throws Exception {
        // Arrange
        Participant participant = new Participant();
        participant.setEmail("user@example.com");
        participant.setRiskDecision("low");
        Participant saved = participantRepository.save(participant);
        
        // Act & Assert
        mockMvc.perform(get("/api/participants/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andExpect(jsonPath("$.id").value(saved.getId()));
    }
    
    @Test
    void getParticipantById_ShouldReturnNotFound_WhenNotExists() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/participants/999"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void getParticipantByEmail_ShouldReturnParticipant_WhenExists() throws Exception {
        // Arrange
        Participant participant = new Participant();
        participant.setEmail("user@example.com");
        participant.setRiskDecision("low");
        participantRepository.save(participant);
        
        // Act & Assert
        mockMvc.perform(get("/api/participants/email/user@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@example.com"));
    }
    
    @Test
    void getParticipantByEmail_ShouldReturnNotFound_WhenNotExists() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/participants/email/nonexistent@example.com"))
                .andExpect(status().isNotFound());
    }
}
