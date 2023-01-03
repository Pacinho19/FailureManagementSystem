package pl.pacinho.failuremanagementsystem.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {

    NEW(null),
    IN_PROGRESS("#F8C471 "),
    SUSPENDED("#85929E"),
    DONE("#5DADE2"),
    CONFIRMED("#7DCEA0");

    @Getter
    private final String color;

}
