package pl.pacinho.failuremanagementsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pacinho.failuremanagementsystem.exception.TaskNotFoundException;
import pl.pacinho.failuremanagementsystem.model.entity.TaskMessage;
import pl.pacinho.failuremanagementsystem.model.enums.Department;
import pl.pacinho.failuremanagementsystem.model.enums.Status;
import pl.pacinho.failuremanagementsystem.ui.model.NewMessage;
import pl.pacinho.failuremanagementsystem.ui.model.mapper.TaskDtoMapper;
import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.repository.TaskRepository;
import pl.pacinho.failuremanagementsystem.ui.model.NewTaskDto;
import pl.pacinho.failuremanagementsystem.ui.model.TaskDto;

import java.util.List;

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

    public Task getByNumber(long number) {
        return taskRepository.getById(number);
    }

    @Transactional
    public void addMessage(long number, NewMessage newMessage, User user) {
        Task task = getByNumber(number);
        task.addMessage(user, newMessage.getText());
    }

    public List<TaskDto> findByDepartment(Department department) {
        return taskRepository.findByTargetDepartment(department)
                .stream()
                .map(TaskDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public void assign(long number, User user) {
        Task task = getByNumber(number);
        if (task.getTargetDepartment() != user.getDepartment())
            throw new IllegalStateException("Cannot assign task number " + number + ". No permission for task.");

        if (task.getStatus() != Status.NEW)
            throw new IllegalStateException("Cannot assign task number " + number + ". Task status: " + task.getStatus());

        if (task.getExecutor() != null)
            throw new IllegalStateException("Cannot assign task number " + number + ". Task just assign to " + task.getExecutor().getName());

        task.setStatus(Status.IN_PROGRESS);
        task.setExecutor(user);
        task.addMessage(user, "Task in progress...");
    }
}
