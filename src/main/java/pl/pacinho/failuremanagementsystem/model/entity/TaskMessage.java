package pl.pacinho.failuremanagementsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.pacinho.failuremanagementsystem.model.enums.MessageType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class TaskMessage {

    @Id
    @GenericGenerator(name = "taskMessageIdGen", strategy = "increment")
    @GeneratedValue(generator = "taskMessageIdGen")
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID")
    @JsonIgnore
    private Task task;

    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_MESSAGE_ID")
    private TaskMessage parent;

    public TaskMessage(String text, User user, Task task, MessageType type, TaskMessage parent) {
        this.text = text;
        this.user = user;
        this.task = task;
        this.type = type;
        this.parent = parent;
        this.creationDate = LocalDateTime.now();
    }
}
