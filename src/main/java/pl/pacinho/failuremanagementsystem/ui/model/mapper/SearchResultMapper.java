package pl.pacinho.failuremanagementsystem.ui.model.mapper;

import pl.pacinho.failuremanagementsystem.repository.model.SearchResultI;
import pl.pacinho.failuremanagementsystem.ui.model.SearchResultDto;
import pl.pacinho.failuremanagementsystem.ui.model.SearchResultItem;

import java.util.List;

public class SearchResultMapper {
    public static List<SearchResultItem> parseItems(List<SearchResultI> items) {
        return items.stream()
                .map(SearchResultMapper::parseItem)
                .toList();
    }

    private static SearchResultItem parseItem(SearchResultI searchResultI) {
        return new SearchResultItem(
                searchResultI.getNumber(),
                searchResultI.getTitle(),
                searchResultI.getType()
        );
    }

    public static SearchResultDto parseDto(List<SearchResultItem> searchResultItems) {
        return new SearchResultDto(
                searchResultItems.get(0).number(),
                searchResultItems.get(0).title(),
                searchResultItems.stream().map(SearchResultItem::type).distinct().toList()
        );
    }
}