package pl.pacinho.failuremanagementsystem.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.pacinho.failuremanagementsystem.model.dto.FmsUserDto;
import pl.pacinho.failuremanagementsystem.model.enums.Department;
import pl.pacinho.failuremanagementsystem.service.UserService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final UserService userService;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        initUsers();
    }

    private void initUsers() {
        if (userService.getUsersCount() > 0) return;

        List.of(
                new FmsUserDto("Patryk", "O", "po", "1", Department.BINT)
        ).forEach(userService::save);
    }

}