package pl.pacinho.failuremanagementsystem.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Event {

    UPDATE_IN_DATABASE("Update data in database"),
    ERROR("Error"),
    DATA_MISSING_OR_REDUNDANT("Data missing / redundant"),
    GENERATE_DOCUMENT("Generate document"),
    MODIFYING("Modifying"),
    UNSTABLE_WORK("Unstable work"),
    NEW_APPLICATION("New application or feature"),
    DATA_DELETION("Data deletion"),
    COMPLETION_OF_DATA("Completion of data"),
    VERIFICATION("Verification / consultation");

    @Getter
    private final String description;
}
