package pl.pacinho.failuremanagementsystem.ui.tools;

import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.model.enums.TaskSummaryType;
import pl.pacinho.failuremanagementsystem.ui.model.TaskDto;
import pl.pacinho.failuremanagementsystem.ui.model.TaskSummaryItem;

import java.util.Arrays;
import java.util.List;

public class TaskStatusUtils {
    public static boolean checkCanFinish(TaskDto task, User user) {
        if (task.getOwner().getId() == user.getId() || task.getTargetDepartment()==user.getDepartment())
            return true;

        if (task.getTaskSummary() == null || task.getTaskSummary().summaryItems().isEmpty())
            return false;

        return Arrays.stream(TaskSummaryType.values())
                .allMatch(tt -> {
                    List<TaskSummaryItem> taskSummaryItems = task.getTaskSummary().summaryItems().get(tt);
                    return taskSummaryItems != null && !taskSummaryItems.isEmpty();
                });
    }
}
