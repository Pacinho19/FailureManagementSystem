package pl.pacinho.failuremanagementsystem.ui.model;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.failuremanagementsystem.model.enums.TaskSummaryType;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Setter
@Getter
public class SummaryInput {

    private Map<TaskSummaryType, String> values;

    public SummaryInput() {
        initMap();
    }

    private void initMap() {
        this.values = Arrays.stream(TaskSummaryType.values())
                .map(t -> Map.entry(t, ""))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
