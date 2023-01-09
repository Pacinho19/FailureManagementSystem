package pl.pacinho.failuremanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.TaskFollow;
import pl.pacinho.failuremanagementsystem.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskFollowRepository extends JpaRepository<TaskFollow, Long> {

    List<TaskFollow> findAllByTaskNumber(long number);

    boolean existsByTaskNumberAndUserUsername(long number, String username);

    Optional<TaskFollow> findByTaskAndUser(Task task, User user);
}
