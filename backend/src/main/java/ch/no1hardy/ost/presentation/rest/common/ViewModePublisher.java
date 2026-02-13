package ch.no1hardy.ost.presentation.rest.common;

import ch.no1hardy.ost.application.common.port.in.RequestViewModeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewModePublisher {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RequestViewModeUseCase requestViewModeUseCase;

    public void publishViewMode() {
        simpMessagingTemplate.convertAndSend("/topic/mode", requestViewModeUseCase.requestViewMode());
    }

}
