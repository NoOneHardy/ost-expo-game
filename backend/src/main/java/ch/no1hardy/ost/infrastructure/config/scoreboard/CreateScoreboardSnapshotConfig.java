package ch.no1hardy.ost.infrastructure.config.scoreboard;

import ch.no1hardy.ost.application.scoreboard.port.in.BuildScoreboardUseCase;
import ch.no1hardy.ost.application.scoreboard.port.in.CreateScoreboardSnapshotUseCase;
import ch.no1hardy.ost.application.scoreboard.port.out.CheckForSnapshotRepoPort;
import ch.no1hardy.ost.application.scoreboard.port.out.StoreScoreboardSnapshotRepoPort;
import ch.no1hardy.ost.application.scoreboard.service.CreateScoreboardSnapshotService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateScoreboardSnapshotConfig {
    @Bean
    public CreateScoreboardSnapshotUseCase createScoreboardSnapshotUseCase(
            BuildScoreboardUseCase buildScoreboardUseCase,
            CheckForSnapshotRepoPort checkForSnapshotRepoPort,
            StoreScoreboardSnapshotRepoPort storeScoreboardSnapshotRepoPort
    ) {
        return new CreateScoreboardSnapshotService(buildScoreboardUseCase, checkForSnapshotRepoPort, storeScoreboardSnapshotRepoPort);
    }
}
