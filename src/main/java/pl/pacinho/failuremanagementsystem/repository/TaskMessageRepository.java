package pl.pacinho.failuremanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pacinho.failuremanagementsystem.model.entity.TaskMessage;

@Repository
public interface TaskMessageRepository extends JpaRepository<TaskMessage, Long> {
}
