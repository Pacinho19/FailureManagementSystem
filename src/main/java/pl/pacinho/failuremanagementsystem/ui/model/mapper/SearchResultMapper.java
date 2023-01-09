package pl.pacinho.failuremanagementsystem.ui.model.mapper;

import pl.pacinho.failuremanagementsystem.ui.model.SearchResultDto;
import pl.pacinho.failuremanagementsystem.ui.model.SearchResultItem;

import java.util.List;

public class SearchResultMapper {

    public static SearchResultDto parseDto(List<SearchResultItem> searchResultItems) {
        return new SearchResultDto(
                searchResultItems.get(0).getNumber().longValue(),
                searchResultItems.get(0).getTitle(),
                searchResultItems.stream().map(SearchResultItem::getType).distinct().toList()
        );
    }
}