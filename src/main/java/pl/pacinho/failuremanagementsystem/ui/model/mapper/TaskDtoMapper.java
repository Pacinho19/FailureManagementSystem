package pl.pacinho.failuremanagementsystem.ui.model.mapper;

import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.ui.model.NewTaskDto;
import pl.pacinho.failuremanagementsystem.ui.model.TaskDto;

public class TaskDtoMapper {
    public static Task toEntity(NewTaskDto newTaskDto, User owner) {
        return new Task(
                newTaskDto.getTitle(),
                newTaskDto.getDeadlineDate(),
                owner,
                newTaskDto.getTaskType(),
                newTaskDto.getEvent(),
                newTaskDto.getTargetDepartment(),
                newTaskDto.getPurpose(),
                newTaskDto.isChangeAssumptions(),
                newTaskDto.getMessage(),
                newTaskDto.getPriority()
        );
    }

    public static TaskDto toDto(Task task) {
        return new TaskDto(
                task
        );
    }
}
