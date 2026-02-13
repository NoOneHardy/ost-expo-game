package ch.no1hardy.ost.service;

import ch.no1hardy.ost.domain.Participant;
import ch.no1hardy.ost.repository.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {
    
    @Mock
    private ParticipantRepository participantRepository;
    
    @InjectMocks
    private ParticipantService participantService;
    
    private Participant testParticipant;
    
    @BeforeEach
    void setUp() {
        testParticipant = new Participant();
        testParticipant.setId(1L);
        testParticipant.setEmail("test@example.com");
        testParticipant.setRiskDecision("low");
    }
    
    @Test
    void saveParticipant_ShouldSaveAndReturnParticipant() {
        // Arrange
        when(participantRepository.save(any(Participant.class))).thenReturn(testParticipant);
        
        // Act
        Participant result = participantService.saveParticipant(testParticipant);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        verify(participantRepository, times(1)).save(testParticipant);
    }
    
    @Test
    void findByEmail_ShouldReturnParticipant_WhenExists() {
        // Arrange
        when(participantRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testParticipant));
        
        // Act
        Optional<Participant> result = participantService.findByEmail("test@example.com");
        
        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("test@example.com");
        verify(participantRepository, times(1)).findByEmail("test@example.com");
    }
    
    @Test
    void findByEmail_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        when(participantRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        
        // Act
        Optional<Participant> result = participantService.findByEmail("nonexistent@example.com");
        
        // Assert
        assertThat(result).isEmpty();
        verify(participantRepository, times(1)).findByEmail("nonexistent@example.com");
    }
    
    @Test
    void existsByEmail_ShouldReturnTrue_WhenExists() {
        // Arrange
        when(participantRepository.existsByEmail("test@example.com")).thenReturn(true);
        
        // Act
        boolean result = participantService.existsByEmail("test@example.com");
        
        // Assert
        assertThat(result).isTrue();
        verify(participantRepository, times(1)).existsByEmail("test@example.com");
    }
    
    @Test
    void existsByEmail_ShouldReturnFalse_WhenNotExists() {
        // Arrange
        when(participantRepository.existsByEmail("nonexistent@example.com")).thenReturn(false);
        
        // Act
        boolean result = participantService.existsByEmail("nonexistent@example.com");
        
        // Assert
        assertThat(result).isFalse();
        verify(participantRepository, times(1)).existsByEmail("nonexistent@example.com");
    }
    
    @Test
    void getAllParticipants_ShouldReturnListOfParticipants() {
        // Arrange
        Participant participant2 = new Participant();
        participant2.setId(2L);
        participant2.setEmail("test2@example.com");
        
        List<Participant> participants = Arrays.asList(testParticipant, participant2);
        when(participantRepository.findAll()).thenReturn(participants);
        
        // Act
        List<Participant> result = participantService.getAllParticipants();
        
        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).contains(testParticipant, participant2);
        verify(participantRepository, times(1)).findAll();
    }
    
    @Test
    void getParticipantById_ShouldReturnParticipant_WhenExists() {
        // Arrange
        when(participantRepository.findById(1L)).thenReturn(Optional.of(testParticipant));
        
        // Act
        Optional<Participant> result = participantService.getParticipantById(1L);
        
        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        verify(participantRepository, times(1)).findById(1L);
    }
}
