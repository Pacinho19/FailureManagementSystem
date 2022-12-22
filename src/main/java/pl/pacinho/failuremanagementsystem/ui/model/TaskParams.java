package pl.pacinho.failuremanagementsystem.ui.model;

import lombok.Getter;
import pl.pacinho.failuremanagementsystem.model.enums.Department;
import pl.pacinho.failuremanagementsystem.model.enums.Event;
import pl.pacinho.failuremanagementsystem.model.enums.Priority;
import pl.pacinho.failuremanagementsystem.model.enums.TaskType;

@Getter
public class TaskParams {

    private static TaskParams self;
    private final Department[] departments;
    private final TaskType[] taskTypes;
    private final Event[] events;
    private final Priority[] priorities;

    public TaskParams() {
        departments = Department.values();
        taskTypes = TaskType.values();
        events = Event.values();
        priorities = Priority.values();
    }

    public static TaskParams instance() {
        if (self == null) self = new TaskParams();
        return self;
    }
}
