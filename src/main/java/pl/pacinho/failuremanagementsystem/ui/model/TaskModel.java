package pl.pacinho.failuremanagementsystem.ui.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.pacinho.failuremanagementsystem.model.enums.Department;
import pl.pacinho.failuremanagementsystem.model.enums.Event;
import pl.pacinho.failuremanagementsystem.model.enums.Priority;
import pl.pacinho.failuremanagementsystem.model.enums.TaskType;

import java.time.LocalDate;

@Setter
@Getter
public abstract class TaskModel {

    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadlineDate;
    private TaskType taskType;
    private Event event;
    private Department targetDepartment;
    private Priority priority;
    private String purpose;
    private boolean changeAssumptions;




}
