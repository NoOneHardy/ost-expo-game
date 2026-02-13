package ch.no1hardy.ost.presentation.rest.common;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CommonController {
    private final ViewModePublisher viewModePublisher;

    @MessageMapping("/mode")
    @SendTo("/topic/mode")
    public void sendMode() {
        viewModePublisher.publishViewMode();
    }
}
