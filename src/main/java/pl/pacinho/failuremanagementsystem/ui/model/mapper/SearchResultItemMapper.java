package pl.pacinho.failuremanagementsystem.ui.model.mapper;

import pl.pacinho.failuremanagementsystem.ui.model.SearchResultItem;
import pl.pacinho.failuremanagementsystem.ui.model.enums.SearchType;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.util.List;

public class SearchResultItemMapper {

    public static List<SearchResultItem> parseList(List<Tuple> tuples) {
        return tuples
                .stream()
                .map(SearchResultItemMapper::parseItem)
                .toList();
    }

    private static SearchResultItem parseItem(Tuple tuple) {
        return new SearchResultItem(
                ((BigDecimal) tuple.get("number")).longValue(),
                (String) tuple.get("title"),
                SearchType.fromString((String) tuple.get("type"))
        );
    }
}