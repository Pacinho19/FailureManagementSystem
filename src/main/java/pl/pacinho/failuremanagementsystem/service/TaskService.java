package pl.pacinho.failuremanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.pacinho.failuremanagementsystem.exception.TaskNotFoundException;
import pl.pacinho.failuremanagementsystem.model.entity.TaskMessage;
import pl.pacinho.failuremanagementsystem.model.enums.AttachmentSource;
import pl.pacinho.failuremanagementsystem.model.enums.Department;
import pl.pacinho.failuremanagementsystem.model.enums.Status;
import pl.pacinho.failuremanagementsystem.ui.model.NewMessage;
import pl.pacinho.failuremanagementsystem.ui.model.enums.NotificationMessage;
import pl.pacinho.failuremanagementsystem.ui.model.mapper.TaskDtoMapper;
import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.repository.TaskRepository;
import pl.pacinho.failuremanagementsystem.ui.model.NewTaskDto;
import pl.pacinho.failuremanagementsystem.ui.model.TaskDto;
import pl.pacinho.failuremanagementsystem.ui.tools.SystemMessages;
import pl.pacinho.failuremanagementsystem.utils.AttachmentUtils;
import pl.pacinho.failuremanagementsystem.utils.CollectionsUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMessageService taskMessageService;

    private final NotificationService notificationService;

    @Transactional
    public Task save(NewTaskDto newTaskDto, User owner) {
        return taskRepository.save(TaskDtoMapper.toEntity(newTaskDto, owner));
    }

    public TaskDto findByNumber(long number) {
        return TaskDtoMapper.toDto(taskRepository.findById(number).orElseThrow(() -> new TaskNotFoundException(number)));
    }

    public Task getByNumber(long number) {
        return taskRepository.findById(number)
                .orElseThrow(() -> new TaskNotFoundException(number));
    }

    @Transactional
    public void addMessage(long number, NewMessage newMessage, User user, Long parentMessage) {
        Task task = getByNumber(number);

        TaskMessage taskMessage = parentMessage != null
                ? taskMessageService.findById(parentMessage)
                : null;

        task.addMessage(user, newMessage.getText(), taskMessage);
        notificationService.addNotifications(NotificationMessage.NEW_MESSAGE, number, task, user);
    }

    public List<TaskDto> findByDepartmentNotConfirmed(Department department) {
        return taskRepository.findByTargetDepartmentAndStatusNot(department, Status.CONFIRMED)
                .stream()
                .map(TaskDtoMapper::toDto)
                .toList();
    }

    public List<TaskDto> findByOwnerNotConfirmed(User user) {
        return taskRepository.findByOwnerAndStatusNot(user, Status.CONFIRMED)
                .stream()
                .map(TaskDtoMapper::toDto)
                .toList();
    }

    public List<TaskDto> findByDepartmentOrOwnerNotConfirmed(Department department, User user) {
        return Stream.of(
                        findByDepartmentNotConfirmed(department),
                        findByOwnerNotConfirmed(user)
                )
                .flatMap(List::stream)
                .filter(CollectionsUtils.distinctByKey(TaskDto::getNumber))
                .toList();
    }

    @Transactional
    public void assign(long number, User user) {
        Task task = getByNumber(number);
        if (task.getTargetDepartment() != user.getDepartment())
            throw new IllegalStateException("No permission for change task " + number + " status ");

        if (task.getStatus() != Status.NEW)
            throw new IllegalStateException("Cannot assign task number " + number + ". Task status: " + task.getStatus());

        if (task.getExecutor() != null)
            throw new IllegalStateException("Cannot assign task number " + number + ". Task just assign to " + task.getExecutor().getName());

        task.setStatus(Status.IN_PROGRESS);
        task.setExecutor(user);
        task.addSysMessage(user, SystemMessages.TASK_IN_PROGRESS);

        notificationService.addNotifications(NotificationMessage.CHANGE_TASK_STATUS, number, task, user);
    }

    @Transactional
    public void finish(Long number, User user) {
        Task task = getByNumber(number);

        if (task.getStatus() != Status.IN_PROGRESS
                && task.getStatus() != Status.NEW
                && task.getStatus() != Status.SUSPENDED)
            throw new IllegalStateException("Cannot finish task number " + number + ". Task status: " + task.getStatus());

        if (task.getExecutor() == null && task.getExecutor().getDepartment() == user.getDepartment()
                || task.getExecutor() != null && task.getExecutor().getId() == user.getId()
                || task.getOwner().getId() == user.getId()) {

            task.setStatus(Status.DONE);
            task.addSysMessage(user, SystemMessages.TASK_DONE);
            notificationService.addNotifications(NotificationMessage.CHANGE_TASK_STATUS, number, task, user);

        } else
            throw new IllegalStateException("No permission for change task " + number + " status ");

    }

    @Transactional
    public void confirm(Long number, User user) {
        Task task = getByNumber(number);
        if (!Objects.equals(task.getOwner().getId(), user.getId()))
            throw new IllegalStateException("No permission for change task " + number + " status ");

        if (task.getStatus() != Status.DONE)
            throw new IllegalStateException("Cannot confirm task number " + number + ". Task status: " + task.getStatus());

        task.setStatus(Status.CONFIRMED);
        task.addSysMessage(user, SystemMessages.TASK_CONFIRMED);
        notificationService.addNotifications(NotificationMessage.CHANGE_TASK_STATUS, number, task, user);
    }

    @Transactional
    public void decline(Long number, User user) {
        Task task = getByNumber(number);
        if (!Objects.equals(task.getOwner().getId(), user.getId()))
            throw new IllegalStateException("No permission for change task " + number + " status ");

        if (task.getStatus() != Status.DONE)
            throw new IllegalStateException("Cannot decline task number " + number + ". Task status: " + task.getStatus());

        task.setStatus(Status.IN_PROGRESS);
        task.addSysMessage(user, SystemMessages.TASK_DECLINED);
        notificationService.addNotifications(NotificationMessage.CHANGE_TASK_STATUS, number, task, user);
    }

    @Transactional
    public void suspend(Long number, User user) {
        Task task = getByNumber(number);

        if (task.getStatus() != Status.IN_PROGRESS
                && task.getStatus() != Status.NEW)
            throw new IllegalStateException("Cannot suspended task number " + number + ". Task status: " + task.getStatus());

        if (task.getExecutor() == null && task.getExecutor().getDepartment() == user.getDepartment()
                || task.getExecutor() != null && task.getExecutor().getId() == user.getId()
                || task.getOwner().getId() == user.getId()) {


            task.setStatus(Status.SUSPENDED);
            task.addSysMessage(user, SystemMessages.TASK_SUSPENDED);
            notificationService.addNotifications(NotificationMessage.CHANGE_TASK_STATUS, number, task, user);
        } else
            throw new IllegalStateException("No permission for change task " + number + " status ");

    }

    @Transactional
    public void resume(Long number, User user) {
        Task task = getByNumber(number);
        if (task.getStatus() != Status.SUSPENDED)
            throw new IllegalStateException("Cannot resume task number " + number + ". Task status: " + task.getStatus());

        if (task.getExecutor() == null && task.getExecutor().getDepartment() == user.getDepartment()
                || task.getExecutor() != null && task.getExecutor().getId() == user.getId()
                || task.getOwner().getId() == user.getId()) {

            task.setStatus(task.getExecutor() != null ? Status.IN_PROGRESS : Status.NEW);
            task.addSysMessage(user, SystemMessages.TASK_RESUMED);
            notificationService.addNotifications(NotificationMessage.CHANGE_TASK_STATUS, number, task, user);
        } else
            throw new IllegalStateException("No permission for change task " + number + " status ");

    }

    @Transactional
    public void addAttachment(long number, MultipartFile file, User user, AttachmentSource source) {
        Task task = getByNumber(number);
        if (task.getStatus() != Status.NEW
                && task.getStatus() != Status.IN_PROGRESS)
            throw new IllegalStateException("Cannot add attachment. Task status: " + task.getStatus());

        String outPath = AttachmentUtils.save(number, file);
        task.addAttachment(outPath, file.getOriginalFilename(), user, source);
        notificationService.addNotifications(NotificationMessage.NEW_ATTACHMENT, number, task, user);
    }

    @Transactional
    public void bind(User user, long number, Long relatedTaskNumber) {
        if (number == relatedTaskNumber)
            throw new IllegalStateException("Cannot bind self.");

        Task task = getByNumber(number);

        if (isRelatedCase(task, relatedTaskNumber))
            throw new IllegalStateException("Cannot bind task. Task just linked");

        Task relatedTask = getByNumber(relatedTaskNumber);

        task.addRelatedTask(relatedTask);
        task.addSysMessage(user, SystemMessages.TASK_ADD_RELATED + relatedTaskNumber);
        relatedTask.addRelatedTask(task);
        relatedTask.addSysMessage(user, SystemMessages.TASK_ADD_RELATED + number);

        notificationService.addNotifications(NotificationMessage.NEW_RELATED_TASK, number, task, user);
        notificationService.addNotifications(NotificationMessage.NEW_RELATED_TASK, number, relatedTask, user);
    }

    private boolean isRelatedCase(Task task, long relatedTaskNumber) {
        return task.getRelatedTasks()
                .stream()
                .anyMatch(t -> Objects.equals(t.getNumber(), relatedTaskNumber));
    }
}