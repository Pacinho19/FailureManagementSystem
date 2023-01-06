package pl.pacinho.failuremanagementsystem.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pacinho.failuremanagementsystem.service.NotificationService;
import pl.pacinho.failuremanagementsystem.service.UserService;
import pl.pacinho.failuremanagementsystem.ui.config.UIConfig;

@RequiredArgsConstructor
@Controller
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    @PostMapping(UIConfig.NOTIFICATION_READ)
    public String readNotification(@PathVariable("id") long id,
                                   @RequestParam Long taskNumber) {
        notificationService.read(id);
        return "redirect:" + UIConfig.TASK + "/" + taskNumber;
    }

    @GetMapping(UIConfig.NOTIFICATION_READ_ALL)
    public String readNotification(Authentication authentication) {
        notificationService.readAllByUsername(authentication.getName());
        return "redirect:" + UIConfig.HOME;
    }

    @PostMapping(UIConfig.NOTIFICATION_REFRESH)
    public String notificationFragment(Model model, Authentication authentication) {
        model.addAttribute("notifications", notificationService.findUnreadByUser(userService.getByLogin(authentication.getName())));
        return "fragments/notifications :: notifications";
    }
}