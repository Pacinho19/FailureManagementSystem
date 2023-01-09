package pl.pacinho.failuremanagementsystem.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class TaskFollow {

    @Id
    @GenericGenerator(name = "taskFollowId", strategy = "increment")
    @GeneratedValue(generator = "taskFollowId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public TaskFollow(Task task, User user) {
        this.task = task;
        this.user = user;
    }
}
