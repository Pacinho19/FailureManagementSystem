package pl.pacinho.failuremanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.model.enums.Department;
import pl.pacinho.failuremanagementsystem.model.enums.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTargetDepartmentAndStatusNot(Department targetDepartment, Status status);
    List<Task> findByOwnerAndStatusNot(User user, Status status);
    List<Task> findByTargetDepartmentAndStatusEquals(Department department, Status status);
    List<Task> findByOwnerAndStatusEquals(User user, Status status);
    List<Task> findByOwnerDepartmentEqualsAndStatusEquals(Department department, Status status);
}