package pl.pacinho.failuremanagementsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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

    public TaskMessage(String text, User user, Task task) {
        this.text = text;
        this.user = user;
        this.task = task;
        this.creationDate = LocalDateTime.now();
    }
}
