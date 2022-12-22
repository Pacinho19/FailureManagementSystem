package pl.pacinho.failuremanagementsystem.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.pacinho.failuremanagementsystem.model.dto.mapper.UserDtoMapper;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.service.UserService;
import pl.pacinho.failuremanagementsystem.ui.config.UIConfig;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping(UIConfig.PREFIX)
    public String homeFalsi(){
        return "redirect:" + UIConfig.HOME;
    }
    @GetMapping
    public String homeEmpty(){
        return "redirect:" + UIConfig.HOME;
    }

    @GetMapping(UIConfig.HOME)
    public String home(Model model,
                       Authentication authentication) {

        User user = userService.getByLogin(authentication.getName());
        model.addAttribute("user", UserDtoMapper.parseToDto(user));
        return "home";
    }
}
