package pl.pacinho.failuremanagementsystem.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import pl.pacinho.failuremanagementsystem.model.enums.*;
import pl.pacinho.failuremanagementsystem.model.enums.Status;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
public class Task {

    @Id
    @GenericGenerator(name = "taskIdGen", strategy = "increment")
    @GeneratedValue(generator = "taskIdGen")
    private Long number;

    private String title;

    private LocalDateTime creationDate;
    private LocalDate deadlineDate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Setter
    @ManyToOne
    @JoinColumn(name = "executor_id")
    private User executor;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @Enumerated(EnumType.STRING)
    private Event event;

    @Enumerated(EnumType.STRING)
    private Department targetDepartment;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Setter
    @Enumerated(EnumType.STRING)
    private Status status;

    private String purpose;

    private boolean changeAssumptions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task", cascade = CascadeType.ALL)
    private List<Attachment> attachments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskMessage> messages;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Task> relatedTasks;

    public Task(String title, LocalDate deadlineDate, User owner, TaskType taskType, Event event, Department targetDepartment, String purpose, boolean changeAssumptions, String message, Priority priority) {
        this.title = title;
        this.deadlineDate = deadlineDate;
        this.owner = owner;
        this.taskType = taskType;
        this.event = event;
        this.targetDepartment = targetDepartment;
        this.purpose = purpose;
        this.changeAssumptions = changeAssumptions;
        this.priority = priority;
        this.creationDate = LocalDateTime.now();
        this.attachments = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.relatedTasks = new HashSet<>();
        this.status = Status.NEW;
        addMessage(owner, message, null);
    }

    private void addMessage(User user, String message, MessageType messageType, TaskMessage parent) {
        this.messages.add(
                new TaskMessage(message, user, this, messageType, parent)
        );
    }

    public void addMessage(User user, String message, TaskMessage parent) {
        addMessage(user, message, MessageType.USER, parent);
    }

    public void addSysMessage(User user, String message) {
        addMessage(user, message, MessageType.SYS, null);
    }

    public void addAttachment(String path, String originalName, User user, AttachmentSource source) {
        attachments.add(
                new Attachment(path, originalName, this, user, source)
        );
    }
    public void addRelatedTask(Task task){
        relatedTasks.add(task);
    }

}
