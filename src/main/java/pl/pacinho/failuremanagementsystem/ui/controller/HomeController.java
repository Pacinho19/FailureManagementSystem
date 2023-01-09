package pl.pacinho.failuremanagementsystem.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.model.enums.TaskKind;
import pl.pacinho.failuremanagementsystem.service.TaskService;
import pl.pacinho.failuremanagementsystem.service.UserService;
import pl.pacinho.failuremanagementsystem.ui.config.UIConfig;
import pl.pacinho.failuremanagementsystem.ui.model.TaskDto;
import pl.pacinho.failuremanagementsystem.ui.tools.CommonObjects;
import pl.pacinho.failuremanagementsystem.ui.tools.TaskUtils;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CommonObjects commonObjects;
    private final TaskService taskService;
    private final UserService userService;

    @GetMapping(UIConfig.PREFIX)
    public String homeFalsi() {
        return "redirect:" + UIConfig.HOME;
    }

    @GetMapping
    public String homeEmpty() {
        return "redirect:" + UIConfig.HOME;
    }

    @GetMapping(UIConfig.HOME)
    public String home(Model model,
                       Authentication authentication) {

        User user = commonObjects.setData(model, authentication);
        Map<TaskKind, List<TaskDto>> tasksKinds = TaskUtils.groupNotConfirmedByKind(
                taskService.findByDepartmentOrOwnerNotConfirmed(user.getDepartment(), user),
                user
        );

        model.addAttribute("myTasks", tasksKinds.get(TaskKind.OWN));
        model.addAttribute("departmentTasks", tasksKinds.get(TaskKind.DEP));
        model.addAttribute("doneTasks", tasksKinds.get(TaskKind.DONE));
        model.addAttribute("requestedTasks", tasksKinds.get(TaskKind.REQUESTED));

        return "home";
    }
}