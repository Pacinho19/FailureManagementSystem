package pl.pacinho.failuremanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.failuremanagementsystem.exception.TaskMessageNotFoundException;
import pl.pacinho.failuremanagementsystem.model.entity.TaskMessage;
import pl.pacinho.failuremanagementsystem.repository.TaskMessageRepository;

@RequiredArgsConstructor
@Service
public class TaskMessageService {

    private final TaskMessageRepository taskMessageRepository;

    public TaskMessage findById(Long id) {
        return taskMessageRepository.findById(id)
                .orElseThrow(() -> new TaskMessageNotFoundException(id));
    }
}
