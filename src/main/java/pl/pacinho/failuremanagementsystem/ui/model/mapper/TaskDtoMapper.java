package pl.pacinho.failuremanagementsystem.ui.model.mapper;

import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.ui.model.NewTaskDto;
import pl.pacinho.failuremanagementsystem.ui.model.RelatedTaskDto;
import pl.pacinho.failuremanagementsystem.ui.model.TaskDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private static RelatedTaskDto toRelatedTaskDto(Task task) {
        return new RelatedTaskDto(
                task.getNumber(),
                task.getTitle(),
                task.getStatus()
        );
    }

    public static List<TaskDto> parseList(Set<Task> tasks) {
        return tasks.stream()
                .map(TaskDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<RelatedTaskDto> parseRelatedTasks(Set<Task> tasks) {
        return tasks.stream()
                .map(TaskDtoMapper::toRelatedTaskDto)
                .collect(Collectors.toList());
    }

}
