package pl.pacinho.failuremanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.model.enums.Department;
import pl.pacinho.failuremanagementsystem.model.enums.Status;
import pl.pacinho.failuremanagementsystem.repository.model.SearchResultI;
import pl.pacinho.failuremanagementsystem.repository.queries.NativeQueries;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTargetDepartmentAndStatusNot(Department targetDepartment, Status confirmed);
    List<Task> findByOwnerAndStatusNot(User user, Status confirmed);

    @Query(nativeQuery = true, value = NativeQueries.TASK_SEARCH)
    List<SearchResultI> search(@Param("searchText") String searchText);
}