package pl.pacinho.failuremanagementsystem.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import pl.pacinho.failuremanagementsystem.model.enums.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        this.status = Status.NEW;
        addMessage(owner, message);
    }

    public void addMessage(User owner, String message) {
        this.messages.add(
                new TaskMessage(message, owner, this)
        );
    }

    public void addAttachment(String path) {
        attachments.add(
                new Attachment(path, this.number)
        );
    }

}
