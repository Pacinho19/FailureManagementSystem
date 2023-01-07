package pl.pacinho.failuremanagementsystem.ui.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.pacinho.failuremanagementsystem.ui.model.enums.SearchResultType;

import java.util.List;

@AllArgsConstructor
@Getter
public class SearchResultDto {

    private long number;
    private String title;
    private List<SearchResultType> types;
}