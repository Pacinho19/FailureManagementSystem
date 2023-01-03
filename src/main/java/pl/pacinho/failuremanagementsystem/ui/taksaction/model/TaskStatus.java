package pl.pacinho.failuremanagementsystem.ui.taksaction.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public enum TaskStatus {

    NEW(List.of(TaskAction.ASSIGN, TaskAction.HOLD, TaskAction.FINISH)),
    IN_PROGRESS(List.of(TaskAction.HOLD, TaskAction.FINISH)),
    SUSPENDED(List.of(TaskAction.RESUME, TaskAction.FINISH)),
    DONE(List.of(TaskAction.CONFIRM, TaskAction.DECLINE)),
    CONFIRMED(Collections.emptyList());

    @Getter
    private final List<TaskAction> actions;
}
