package ch.no1hardy.ost.controller;

import ch.no1hardy.ost.infrastructure.persistence.mongo.document.Scoreboard;
import ch.no1hardy.ost.infrastructure.persistence.mongo.repository.ScoreboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class HealthController {
    private final ScoreboardRepository repository;

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "OST Expo Game Backend");
        return response;
    }

    @GetMapping("/health/test-db")
    public Map<String, String> testDatabase() {
        Map<String, String> response = new HashMap<>();
        try {
            Scoreboard score = new Scoreboard();
            score.setId(UUID.randomUUID().toString());
            repository.save(new Scoreboard());
            response.put("database", repository.findAll().toString());
        } catch (Exception e) {
            response.put("database", "DOWN");
            response.put("error", e.getMessage());
        }
        return response;
    }
}
