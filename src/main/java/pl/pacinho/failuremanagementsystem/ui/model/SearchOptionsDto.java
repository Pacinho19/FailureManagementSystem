package pl.pacinho.failuremanagementsystem.ui.model;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.failuremanagementsystem.ui.model.enums.SearchType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchOptionsDto {

    private String searchText;
    private List<SearchType> types;
    private List<SearchType> selectedTypes;

    public SearchOptionsDto() {
        this.types = List.of(SearchType.values());
    }

    public void init() {
        selectedTypes = new ArrayList<>(types);
    }
}