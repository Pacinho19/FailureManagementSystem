package pl.pacinho.failuremanagementsystem.ui.model.enums;

import lombok.RequiredArgsConstructor;
import pl.pacinho.failuremanagementsystem.utils.NotificationMessageUtils;

import java.util.function.Function;

@RequiredArgsConstructor
public enum NotificationMessage {

    NEW_MESSAGE(NotificationMessageUtils.NEW_MESSAGE),
    CHANGE_TASK_STATUS(NotificationMessageUtils.CHANGE_TASK_STATUS),
    NEW_ATTACHMENT(NotificationMessageUtils.NEW_ATTACHMENT),
    NEW_RELATED_TASK(NotificationMessageUtils.NEW_RELATED_TASK);

    private final Function<Long, String> function;

    public String getMessage(long number) {
        return function.apply(number);
    }
}