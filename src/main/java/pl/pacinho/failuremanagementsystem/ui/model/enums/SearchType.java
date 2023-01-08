package pl.pacinho.failuremanagementsystem.ui.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SearchType {

    NUMBER("badge badge-secondary"),
    TITLE("badge badge-primary"),
    PURPOSE("badge badge-info"),
    MESSAGE("badge badge-warning"),
    ATTACHMENT_NAME("badge badge-dark");

    @Getter
    private final String badgeClass;
}