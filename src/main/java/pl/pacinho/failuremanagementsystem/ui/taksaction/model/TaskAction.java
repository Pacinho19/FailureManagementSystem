package pl.pacinho.failuremanagementsystem.ui.taksaction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.ui.taksaction.TaskActionService;

import java.util.function.BiConsumer;

@AllArgsConstructor
@RequiredArgsConstructor
public enum TaskAction {

    ASSIGN("btn btn-warning", TaskActionService.getInstance().ASSIGN),
    SUSPEND("btn btn-light", TaskActionService.getInstance().SUSPEND),
    RESUME("btn btn-secondary", TaskActionService.getInstance().RESUME),
    FINISH("btn btn-primary",  TaskActionService.getInstance().FINISH),
    CONFIRM("btn btn-success", TaskActionService.getInstance().CONFIRM),
    DECLINE("btn btn-danger",  TaskActionService.getInstance().DECLINE);

    @Getter
    private String buttonClass;
    private BiConsumer<Long, User> action;

    public void goAction(Long id, User user) {
        if (this.action == null)
            throw new IllegalStateException("Action " + this.name() + " not implemented yet!");
        this.action.accept(id, user);
    }

}
