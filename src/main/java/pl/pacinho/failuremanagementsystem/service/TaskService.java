package pl.pacinho.failuremanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.pacinho.failuremanagementsystem.exception.TaskNotFoundException;
import pl.pacinho.failuremanagementsystem.model.entity.TaskMessage;
import pl.pacinho.failuremanagementsystem.model.entity.TaskSummary;
import pl.pacinho.failuremanagementsystem.model.enums.AttachmentSource;
import pl.pacinho.failuremanagementsystem.model.enums.Department;
import pl.pacinho.failuremanagementsystem.model.enums.Status;
import pl.pacinho.failuremanagementsystem.ui.model.*;
import pl.pacinho.failuremanagementsystem.ui.model.enums.NotificationMessage;
import pl.pacinho.failuremanagementsystem.ui.model.mapper.TaskDtoMapper;
import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.repository.TaskRepository;
import pl.pacinho.failuremanagementsystem.ui.tools.SystemMessages;
import pl.pacinho.failuremanagementsystem.ui.tools.TaskStatusUtils;
import pl.pacinho.failuremanagementsystem.utils.AttachmentUtils;
import pl.pacinho.failuremanagementsystem.utils.CollectionsUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMessageService taskMessageService;
    private final NotificationService notificationService;
    private final SearchService searchService;
    private final TaskFollowService taskFollowService;

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

    public List<TaskDto> findByDepartmentAndStatusNot(Department department, Status status) {
        return taskRepository.findByTargetDepartmentAndStatusNot(department, status)
                .stream()
                .map(TaskDtoMapper::toDto)
                .toList();
    }

    public List<TaskDto> findByOwnerNotAndStatusNot(User user, Status status) {
        return taskRepository.findByOwnerAndStatusNot(user, status)
                .stream()
                .map(TaskDtoMapper::toDto)
                .toList();
    }


    private List<TaskDto> findByOwnerDepartmentAndStatusEquals(Department department, Status status) {
        return taskRepository.findByOwnerDepartmentEqualsAndStatusEquals(department, status)
                .stream()
                .map(TaskDtoMapper::toDto)
                .toList();
    }

    private List<TaskDto> findByDepartmentAndStatusEquals(Department department, Status status) {
        return taskRepository.findByTargetDepartmentAndStatusEquals(department, status)
                .stream()
                .map(TaskDtoMapper::toDto)
                .toList();
    }

    private List<TaskDto> findByOwnerNotAndStatusEquals(User user, Status status) {
        return taskRepository.findByOwnerAndStatusEquals(user, status)
                .stream()
                .map(TaskDtoMapper::toDto)
                .toList();
    }

    public List<TaskDto> findByDepartmentOrOwnerConfirmed(Department department, User user) {
        return Stream.of(
                        findByDepartmentAndStatusEquals(department, Status.CONFIRMED),
                        findByOwnerNotAndStatusEquals(user, Status.CONFIRMED),
                        findByOwnerDepartmentAndStatusEquals(department, Status.CONFIRMED)
                )
                .flatMap(List::stream)
                .filter(CollectionsUtils.distinctByKey(TaskDto::getNumber))
                .sorted(Comparator.comparing(TaskDto::getNumber).reversed())
                .toList();
    }

    public List<TaskDto> findByDepartmentOrOwnerNotConfirmed(Department department, User user) {
        return Stream.of(
                        findByDepartmentAndStatusNot(department, Status.CONFIRMED),
                        findByOwnerNotAndStatusNot(user, Status.CONFIRMED)
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

        if (!TaskStatusUtils.checkCanFinish(TaskDtoMapper.toDto(task), user)  )
            throw new IllegalStateException("Cannot finish task number " + number + ". Task summary must be  filled!");

            if ((task.getExecutor() == null && task.getTargetDepartment() == user.getDepartment())
                || (task.getExecutor() != null && Objects.equals(task.getExecutor().getId(), user.getId()))
                || (Objects.equals(task.getOwner().getId(), user.getId()))) {

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

        task.setStatus(task.getExecutor()!=null ? Status.IN_PROGRESS : Status.NEW);
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
            || task.getExecutor() != null && Objects.equals(task.getExecutor().getId(), user.getId())
            || Objects.equals(task.getOwner().getId(), user.getId())) {


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
            || task.getExecutor() != null && Objects.equals(task.getExecutor().getId(), user.getId())
            || Objects.equals(task.getOwner().getId(), user.getId())) {

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

    public void followTask(User user, long number, boolean state) {
        Task task = getByNumber(number);
        if (user.getDepartment() != task.getOwner().getDepartment()
            && task.getTargetDepartment() != user.getDepartment())
            throw new IllegalStateException("No permission for follow task " + number);

        if (Objects.equals(task.getOwner().getId(), user.getId()))
            throw new IllegalStateException("Cannot unfollow. You are Owner!");

        if (task.getExecutor() != null && Objects.equals(task.getExecutor().getId(), user.getId()))
            throw new IllegalStateException("Cannot unfollow. You are Executor!");

        taskFollowService.changeFollowStatus(task, user, state);
    }

    public FollowButton getFollowButton(TaskDto task, User user) {
        if (task.getOwner().getId() == user.getId()
            || (task.getExecutor() != null && task.getExecutor().getId() == user.getId()))
            return FollowButton.UNFOLLOW;

        if (taskFollowService.isFollow(task.getNumber(), user.getUsername()))
            return FollowButton.UNFOLLOW;

        return FollowButton.FOLLOW;
    }

    private boolean isRelatedCase(Task task, long relatedTaskNumber) {
        return task.getRelatedTasks()
                .stream()
                .anyMatch(t -> Objects.equals(t.getNumber(), relatedTaskNumber));
    }

    public List<SearchResultDto> search(SearchOptionsDto searchOptionsDto) {
        return searchService.search(searchOptionsDto);
    }

    @Transactional
    public void addSummary(long number, User user, SummaryInput summaryInput) {
        Task task = getByNumber(number);

        if (task.getExecutor() != null && !Objects.equals(task.getExecutor().getId(), user.getId())
            || (task.getExecutor() == null && task.getTargetDepartment() != user.getDepartment()))
            throw new IllegalStateException("No permission for  adding summary");

        if (task.getStatus() == Status.CONFIRMED || task.getStatus() == Status.SUSPENDED)
            throw new IllegalStateException("Cannot adding summary message. Task status: " + task.getStatus());

        summaryInput.getValues()
                .forEach((type, text) -> {
                    if (text == null || text.isEmpty()) return;

                    task.addSummary(
                            new TaskSummary(user, task, text, type)
                    );
                });
    }
}