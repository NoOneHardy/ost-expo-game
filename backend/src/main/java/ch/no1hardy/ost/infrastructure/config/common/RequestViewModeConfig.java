package ch.no1hardy.ost.infrastructure.config.common;

import ch.no1hardy.ost.application.common.port.in.RequestViewModeUseCase;
import ch.no1hardy.ost.application.common.service.RequestViewModeService;
import ch.no1hardy.ost.application.race.port.out.OpenRaceRepoPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestViewModeConfig {
    @Bean
    public RequestViewModeUseCase requestViewModeUseCase(
            OpenRaceRepoPort openRaceRepoPort
    ) {
        return new RequestViewModeService(openRaceRepoPort);
    }
}
