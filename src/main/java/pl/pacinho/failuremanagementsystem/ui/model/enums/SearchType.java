package pl.pacinho.failuremanagementsystem.ui.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.pacinho.failuremanagementsystem.repository.queries.NativeQueries;

@RequiredArgsConstructor
public enum SearchType {

    NUMBER("badge badge-secondary", NativeQueries.TASK_SEARCH_BY_NUMBER),
    TITLE("badge badge-primary", NativeQueries.TASK_SEARCH_BY_TITLE),
    PURPOSE("badge badge-info", NativeQueries.TASK_SEARCH_BY_PURPOSE),
    MESSAGE("badge badge-warning", NativeQueries.TASK_SEARCH_BY_MESSAGE),
    ATTACHMENT_NAME("badge badge-dark", NativeQueries.TASK_SEARCH_BY_ATTACHMENT_NAME),;

    @Getter
    private final String badgeClass;
    @Getter
    private final String query;
}