package pl.pacinho.failuremanagementsystem.ui.model;

import pl.pacinho.failuremanagementsystem.model.enums.TaskSummaryType;

import java.util.List;
import java.util.Map;

public record TaskSummaryDto(Map<TaskSummaryType, List<TaskSummaryItem>> summaryItems) {

}
