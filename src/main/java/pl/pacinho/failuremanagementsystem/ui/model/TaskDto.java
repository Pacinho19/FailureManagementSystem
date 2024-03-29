package pl.pacinho.failuremanagementsystem.ui.model;

import lombok.Getter;
import pl.pacinho.failuremanagementsystem.model.dto.UserDto;
import pl.pacinho.failuremanagementsystem.model.dto.mapper.UserDtoMapper;
import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.ui.model.mapper.AttachmentDtoMapper;
import pl.pacinho.failuremanagementsystem.ui.model.mapper.MessageDtoMapper;
import pl.pacinho.failuremanagementsystem.ui.model.mapper.TaskDtoMapper;
import pl.pacinho.failuremanagementsystem.ui.model.mapper.TaskSummaryDtoMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Getter
public class TaskDto extends TaskModel {

    private Long number;
    private UserDto owner;
    private UserDto executor;
    private LocalDateTime creationDate;
    private List<MessageDto> messages;
    private List<AttachmentDto> attachments;
    private List<RelatedTaskDto> relatedTasks;

    private TaskSummaryDto taskSummary;

    public TaskDto(Task task) {
        this.setTitle(task.getTitle());
        this.setDeadlineDate(task.getDeadlineDate());
        this.setTaskType(task.getTaskType());
        this.setEvent(task.getEvent());
        this.setTargetDepartment(task.getTargetDepartment());
        this.setPriority(task.getPriority());
        this.setPurpose(task.getPurpose());
        this.setChangeAssumptions(task.isChangeAssumptions());
        this.setStatus(task.getStatus());

        this.number = task.getNumber();
        this.owner = UserDtoMapper.parseToDto(task.getOwner());
        this.executor = UserDtoMapper.parseToDto(task.getExecutor());
        this.creationDate = task.getCreationDate();
        this.messages = MessageDtoMapper.parseList(task.getMessages());
        this.messages.sort(Collections.reverseOrder(Comparator.comparing(MessageDto::dateTime)));
        this.attachments = AttachmentDtoMapper.parseList(task.getAttachments());
        this.relatedTasks = TaskDtoMapper.parseRelatedTasks(task.getRelatedTasks());
        this.taskSummary = TaskSummaryDtoMapper.parse(task.getSummary());
    }

    public long getDeadlineDays() {
        return DAYS.between(LocalDate.now(), this.getDeadlineDate());
    }
}
