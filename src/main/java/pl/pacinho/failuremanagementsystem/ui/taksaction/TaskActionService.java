package pl.pacinho.failuremanagementsystem.ui.taksaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.service.TaskService;

import java.util.function.BiConsumer;

@Component
public class TaskActionService {

    private TaskService taskService;
    private static TaskActionService INSTANCE;

    @Autowired
    public TaskActionService(TaskService taskService) {
        this.taskService = taskService;
        INSTANCE = this;
    }

    public static TaskActionService getInstance() {
        return INSTANCE;
    }

    public final BiConsumer<Long, User> ASSIGN = (taskNumber, user) -> taskService.assign(taskNumber,user);
    public final BiConsumer<Long, User> FINISH =  (taskNumber, user) -> taskService.finish(taskNumber,user);
    public final BiConsumer<Long, User> CONFIRM =  (taskNumber, user) -> taskService.confirm(taskNumber,user);
    public final BiConsumer<Long, User> DECLINE =(taskNumber, user) -> taskService.decline(taskNumber,user);
    public final BiConsumer<Long, User> SUSPEND =(taskNumber, user) -> taskService.suspend(taskNumber,user);
    public final BiConsumer<Long, User> RESUME =(taskNumber, user) -> taskService.resume(taskNumber,user);


}
