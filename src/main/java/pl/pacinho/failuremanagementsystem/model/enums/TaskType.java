package pl.pacinho.failuremanagementsystem.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TaskType {

    PROJECT("New project, application, process etc."),
    FAILURE("Failure preventing operation"),
    VERIFICATION("Verification of the work of the existing application"),
    MODIFICATION("Modification of existing application. New feature or modification existing feature");

    @Getter
    private final String description;
}
