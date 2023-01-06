package pl.pacinho.failuremanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.ui.model.NotificationAlertDto;

@RequiredArgsConstructor
@Controller
public class NotificationWSController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/notification")
    public void notification(User user, String message) {
        simpMessagingTemplate.convertAndSendToUser(user.getUsername(),
                "/notification",
                new NotificationAlertDto(message));

    }

}