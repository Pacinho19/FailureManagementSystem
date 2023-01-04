package pl.pacinho.failuremanagementsystem.ui.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pacinho.failuremanagementsystem.model.dto.mapper.UserDtoMapper;
import pl.pacinho.failuremanagementsystem.model.entity.Attachment;
import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.model.enums.AttachmentSource;
import pl.pacinho.failuremanagementsystem.service.AttachmentService;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Controller
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final AttachmentService attachmentService;

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

        User user = userService.getByLogin(authentication.getName());
        TaskDto task;
        try {
            task = taskService.findByNumber(number);
            TaskUtils.checkTask(task, user);
            model.addAttribute("actions", TaskUtils.filterActions(TaskStatus.valueOf(task.getStatus().name()).getActions(), task, user));
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "task";
        }
        model.addAttribute("user", UserDtoMapper.parseToDto(user));
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
            return taskPage(number, model, authentication);
        }
        return "redirect:" + UIConfig.TASK + "/" + number;
    }

    @PostMapping(UIConfig.TASK_ADD_ATTACHMENT)
    public String addAttachment(@PathVariable("number") long number,
                                @RequestParam("file") MultipartFile file,
                                @RequestParam("source") AttachmentSource source,
                                Authentication authentication,
                                Model model) {
        try {
            taskService.addAttachment(number, file, userService.getByLogin(authentication.getName()), source);
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return taskPage(number, model, authentication);
        }

        return "redirect:" + UIConfig.TASK + "/" + number;
    }

    @PostMapping(UIConfig.ATTACHMENT_DOWNLOAD)
    public void addAttachment(@PathVariable("id") long id,
                                HttpServletResponse response) {
        try {
            File attachment = attachmentService.getAttachment(id);
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename = " + attachment.getName();
            response.setHeader(headerKey, headerValue);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(attachment));
            outputStream.close();
        } catch (Exception ex) {
            //TODO
        }
    }

    @PostMapping(UIConfig.TASK_BIND_TASK)
    public String bindTask(@PathVariable("number") long number,
                           @RequestParam Long taskNumber,
                           Authentication authentication,
                           Model model) {
        return "redirect:" + UIConfig.TASK + "/" + number;
    }
}