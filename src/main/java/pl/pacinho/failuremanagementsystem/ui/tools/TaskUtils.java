package pl.pacinho.failuremanagementsystem.ui.tools;

import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.model.enums.Status;
import pl.pacinho.failuremanagementsystem.model.enums.TaskKind;
import pl.pacinho.failuremanagementsystem.ui.model.TaskDto;
import pl.pacinho.failuremanagementsystem.ui.taksaction.model.TaskAction;

import java.util.ArrayList;
import java.util.Collections;
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
                        filterTasks(tasks, (TaskDto t) -> t.getStatus() == Status.DONE && t.getTargetDepartment()==user.getDepartment())),
                Map.entry(TaskKind.REQUESTED,
                        filterTasks(tasks, (TaskDto t) -> t.getOwner().getId() == user.getId() && t.getStatus()!=Status.CONFIRMED))
        );
    }

    private static List<TaskDto> filterTasks(List<TaskDto> tasks, Predicate<TaskDto> filter) {
        return tasks.stream()
                .filter(filter)
                .toList();
    }

    public static void checkTask(TaskDto task, User user) {
        if (task.getTargetDepartment() != user.getDepartment()
            && user.getDepartment() != task.getOwner().getDepartment())
            throw new IllegalStateException("No permission for open task number " + task.getNumber());
    }

    public static List<TaskAction> filterActions(List<TaskAction> actions, TaskDto task, User user) {
        if (user.getDepartment() != task.getTargetDepartment() && user.getId() != task.getOwner().getId())
            return Collections.emptyList();

        if (task.getExecutor() != null && task.getExecutor().getId() != user.getId() && user.getId() != task.getOwner().getId())
            return Collections.emptyList();

        if (task.getStatus() == Status.DONE && user.getId() != task.getOwner().getId())
            return Collections.emptyList();

        if (task.getStatus() == Status.NEW && task.getTargetDepartment() != user.getDepartment()) {
            ArrayList<TaskAction> actions2 = new ArrayList<>(actions);
            actions2.remove(TaskAction.ASSIGN);
            return actions2;
        }

        return actions;
    }
}
