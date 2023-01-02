package pl.pacinho.failuremanagementsystem.ui.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.pacinho.failuremanagementsystem.model.enums.*;

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
    private Status status;

}
