package pl.pacinho.failuremanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pacinho.failuremanagementsystem.exception.TaskNotFoundException;
import pl.pacinho.failuremanagementsystem.ui.model.mapper.TaskDtoMapper;
import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.repository.TaskRepository;
import pl.pacinho.failuremanagementsystem.ui.model.NewTaskDto;
import pl.pacinho.failuremanagementsystem.ui.model.TaskDto;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public Task save(NewTaskDto newTaskDto, User owner) {
        return taskRepository.save(
                TaskDtoMapper.toEntity(newTaskDto, owner)
        );
    }

    public TaskDto findByNumber(long number) {
        return TaskDtoMapper.toDto(
                taskRepository.findById(number)
                        .orElseThrow(() -> new TaskNotFoundException(number))
        );
    }
}
