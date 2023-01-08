package pl.pacinho.failuremanagementsystem.repository.queries.utils;

import pl.pacinho.failuremanagementsystem.ui.model.SearchOptionsDto;
import pl.pacinho.failuremanagementsystem.ui.model.enums.SearchType;

import java.util.stream.Collectors;

public class SearchQueryUtils {
    private static final String UNION_PART = """
            UNION
            """;

    public static String createQuery(SearchOptionsDto searchOptions) {
        return searchOptions.getSelectedTypes()
                .stream()
                .map(SearchType::getQuery)
                .collect(Collectors.joining(UNION_PART));
    }
}