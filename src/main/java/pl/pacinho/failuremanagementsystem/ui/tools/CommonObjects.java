package pl.pacinho.failuremanagementsystem.ui.tools;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import pl.pacinho.failuremanagementsystem.model.dto.mapper.UserDtoMapper;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.service.NotificationService;
import pl.pacinho.failuremanagementsystem.service.UserService;
import pl.pacinho.failuremanagementsystem.ui.model.SearchOptionsDto;

@RequiredArgsConstructor
@Component
public class CommonObjects {

    private final UserService userService;
    private final NotificationService notificationService;

    public User setData(Model model, Authentication authentication) {
        User user = userService.getByLogin(authentication.getName());
        model.addAttribute("user", UserDtoMapper.parseToDto(user));
        model.addAttribute("notifications", notificationService.findUnreadByUser(user));
        if (model.getAttribute("searchOptions") == null)
            model.addAttribute("searchOptions", new SearchOptionsDto());
        return user;
    }
}