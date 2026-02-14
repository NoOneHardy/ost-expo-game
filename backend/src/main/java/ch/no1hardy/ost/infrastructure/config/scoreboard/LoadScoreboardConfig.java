package ch.no1hardy.ost.infrastructure.config.scoreboard;

import ch.no1hardy.ost.application.scoreboard.port.in.BuildScoreboardUseCase;
import ch.no1hardy.ost.application.scoreboard.port.in.LoadScoreboardUseCase;
import ch.no1hardy.ost.application.scoreboard.service.LoadScoreboardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadScoreboardConfig {
    @Bean
    public LoadScoreboardUseCase loadScoreboardUseCase(
            BuildScoreboardUseCase buildScoreboardUseCase
    ) {
        return new LoadScoreboardService(buildScoreboardUseCase);
    }
}
