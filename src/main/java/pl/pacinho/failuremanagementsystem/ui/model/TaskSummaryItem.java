package pl.pacinho.failuremanagementsystem.ui.model;

import pl.pacinho.failuremanagementsystem.model.dto.UserDto;
import pl.pacinho.failuremanagementsystem.model.enums.TaskSummaryType;

import java.time.LocalDateTime;

public record TaskSummaryItem(UserDto user, String text, LocalDateTime creationTime, TaskSummaryType type) {
}
