package pl.pacinho.failuremanagementsystem.ui.model;

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
public class NewTaskDto extends TaskModel {

    private String message;

    public NewTaskDto() {
        this.setDeadlineDate(LocalDate.now().plusDays(3));
        this.setTaskType(TaskType.VERIFICATION);
        this.setEvent(Event.VERIFICATION);
        this.setTargetDepartment(Department.BINT);
        this.setPriority(Priority.LOW);
        this.setChangeAssumptions(false);
    }
}
