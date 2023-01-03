package pl.pacinho.failuremanagementsystem.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.service.TaskService;
import pl.pacinho.failuremanagementsystem.service.UserService;
import pl.pacinho.failuremanagementsystem.ui.config.UIConfig;
import pl.pacinho.failuremanagementsystem.ui.model.NewMessage;
import pl.pacinho.failuremanagementsystem.ui.model.NewTaskDto;
import pl.pacinho.failuremanagementsystem.ui.model.TaskDto;
import pl.pacinho.failuremanagementsystem.ui.model.TaskParams;
import pl.pacinho.failuremanagementsystem.ui.taksaction.model.TaskAction;
import pl.pacinho.failuremanagementsystem.ui.taksaction.model.TaskStatus;
import pl.pacinho.failuremanagementsystem.ui.tools.TaskUtils;

@RequiredArgsConstructor
@Controller
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @GetMapping(UIConfig.NEW_TASK)
    public String newTask(Model model) {
        model.addAttribute("taskParams", TaskParams.instance());
        model.addAttribute("task", new NewTaskDto());
        return "new-task";
    }

    @PostMapping(UIConfig.NEW_TASK)
    public String newTaskPost(Authentication authentication,
                              NewTaskDto newTaskDto) {
        Task task = taskService.save(newTaskDto, userService.getByLogin(authentication.getName()));
        return "redirect:" + UIConfig.TASK + "/" + task.getNumber();
    }

    @GetMapping(UIConfig.TASK_PAGE)
    public String taskPage(@PathVariable("number") long number,
                           Model model,
                           Authentication authentication) {

        TaskDto task;
        try {
            User user = userService.getByLogin(authentication.getName());
            task = taskService.findByNumber(number);
            TaskUtils.checkTask(task, user);
            model.addAttribute("actions", TaskUtils.filterActions(TaskStatus.valueOf(task.getStatus().name()).getActions(), task, user));
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "task";
        }
        model.addAttribute("task", task);
        model.addAttribute("message", new NewMessage());
        return "task";
    }

    @PostMapping(UIConfig.TASK_SEND_MESSAGE)
    public String sendMessage(@PathVariable("number") long number,
                              Authentication authentication,
                              NewMessage newMessage) {
        taskService.addMessage(number, newMessage, userService.getByLogin(authentication.getName()));
        return "redirect:" + UIConfig.TASK + "/" + number;
    }

    @PostMapping(UIConfig.TASK_ACTION)
    public String taskAction(@PathVariable("number") long number,
                             @RequestParam TaskAction action,
                             Authentication authentication,
                             Model model) {
        try {
            action.goAction(number, userService.getByLogin(authentication.getName()));
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "task";
        }
        return "redirect:" + UIConfig.TASK + "/" + number;
    }
}
