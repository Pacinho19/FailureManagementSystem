package pl.pacinho.failuremanagementsystem.ui.model.mapper;

import pl.pacinho.failuremanagementsystem.model.dto.mapper.UserDtoMapper;
import pl.pacinho.failuremanagementsystem.model.entity.TaskSummary;
import pl.pacinho.failuremanagementsystem.ui.model.TaskSummaryDto;
import pl.pacinho.failuremanagementsystem.ui.model.TaskSummaryItem;

import java.util.List;
import java.util.stream.Collectors;

public class TaskSummaryDtoMapper {
    public static TaskSummaryDto parse(List<TaskSummary> summary) {
        return new TaskSummaryDto(
                summary.stream()
                        .map(TaskSummaryDtoMapper::parseItem)
                        .collect(Collectors.groupingBy(TaskSummaryItem::type))
        );
    }

    private static TaskSummaryItem parseItem(TaskSummary taskSummary) {
        return new TaskSummaryItem(
                UserDtoMapper.parseToDto(taskSummary.getUser()),
                taskSummary.getText(),
                taskSummary.getCreationDate(),
                taskSummary.getType()
        );
    }
}
