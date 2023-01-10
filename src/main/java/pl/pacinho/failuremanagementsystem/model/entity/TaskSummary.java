package pl.pacinho.failuremanagementsystem.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.pacinho.failuremanagementsystem.model.enums.TaskSummaryType;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class TaskSummary {

    @Id
    @GenericGenerator(name = "taskSumaryIdGen", strategy = "increment")
    @GeneratedValue(generator = "taskSumaryIdGen")
    private Long id;

    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private String text;

    @Enumerated(EnumType.STRING)
    private TaskSummaryType type;

    public TaskSummary(User user, Task task, String text, TaskSummaryType type) {
        this.user = user;
        this.task = task;
        this.text = text;
        this.type = type;
        this.creationDate = LocalDateTime.now();
    }


}
