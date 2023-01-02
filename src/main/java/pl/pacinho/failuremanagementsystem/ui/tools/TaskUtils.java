package pl.pacinho.failuremanagementsystem.ui.tools;

import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.model.enums.Status;
import pl.pacinho.failuremanagementsystem.model.enums.TaskKind;
import pl.pacinho.failuremanagementsystem.ui.model.TaskDto;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class TaskUtils {

    public static Map<TaskKind, List<TaskDto>> groupByKind(List<TaskDto> tasks, User user) {
        return Map.ofEntries(
                Map.entry(TaskKind.OWN,
                        filterTasks(tasks, (TaskDto t) -> t.getExecutor() != null && t.getExecutor().getId() == user.getId() && t.getStatus() == Status.IN_PROGRESS)),
                Map.entry(TaskKind.DEP,
                        filterTasks(tasks, (TaskDto t) -> t.getTargetDepartment() == user.getDepartment()
                                                          && (t.getStatus() == Status.NEW
                                                              || t.getStatus() == Status.SUSPENDED
                                                              || (t.getStatus() == Status.IN_PROGRESS && t.getExecutor().getId() != user.getId())))),
                Map.entry(TaskKind.DONE,
                        filterTasks(tasks, (TaskDto t) -> t.getStatus() == Status.DONE))
        );
    }

    private static List<TaskDto> filterTasks(List<TaskDto> tasks, Predicate<TaskDto> filter) {
        return tasks.stream()
                .filter(filter)
                .toList();
    }

    public static void checkTask(TaskDto task, User user) {
        if (task.getTargetDepartment() != user.getDepartment())
            throw new IllegalStateException("No permission for open task number " + task.getNumber());
    }
}
