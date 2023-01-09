package pl.pacinho.failuremanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.TaskFollow;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.repository.TaskFollowRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskFollowService {

    private final TaskFollowRepository taskFollowRepository;

    public List<User> getUsersByTaskNumber(long number) {
        return taskFollowRepository.findAllByTaskNumber(number)
                .stream()
                .map(TaskFollow::getUser)
                .toList();
    }

    public void changeFollowStatus(Task task, User user, boolean state) {
        Optional<TaskFollow> taskFollow = taskFollowRepository.findByTaskAndUser(task, user);
        if (state && taskFollow.isPresent())
            throw new IllegalStateException("Task just follow!");

        if (!state && taskFollow.isEmpty())
            throw new IllegalStateException("Task not follow!");

        if (state)
            taskFollowRepository.save(
                    new TaskFollow(task, user)
            );
        else
            taskFollowRepository.delete(
                    taskFollow.get()
            );

    }

    public boolean isFollow(long number, String username) {
        return taskFollowRepository.existsByTaskNumberAndUserUsername(number, username);
    }
}
