package ch.no1hardy.ost.application.common.service;

import ch.no1hardy.ost.application.common.dto.out.RequestViewModeDto;
import ch.no1hardy.ost.application.common.port.in.RequestViewModeUseCase;
import ch.no1hardy.ost.application.race.port.out.OpenRaceRepoPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestViewModeService implements RequestViewModeUseCase {
    private final OpenRaceRepoPort openRaceRepoPort;

    @Override
    public RequestViewModeDto requestViewMode() {
        return new RequestViewModeDto(openRaceRepoPort.hasOpenRaces());
    }
}
