package ch.no1hardy.ost.infrastructure.config.scoreboard;

import ch.no1hardy.ost.application.scoreboard.port.in.BuildScoreboardUseCase;
import ch.no1hardy.ost.application.scoreboard.port.out.GetLatestScoreboardSnapshotRepoPort;
import ch.no1hardy.ost.application.scoreboard.port.out.GetNewScoresRepoPort;
import ch.no1hardy.ost.application.scoreboard.service.BuildScoreboardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuildScoreboardConfig {
    @Bean
    public BuildScoreboardUseCase buildScoreboardUseCase(
            GetLatestScoreboardSnapshotRepoPort getLatestScoreboardSnapshotRepoPort,
            GetNewScoresRepoPort getNewScoresRepoPort
    ) {
        return new BuildScoreboardService(getLatestScoreboardSnapshotRepoPort, getNewScoresRepoPort);
    }
}
