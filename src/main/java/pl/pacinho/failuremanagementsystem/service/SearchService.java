package pl.pacinho.failuremanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.failuremanagementsystem.repository.SearchRepository;
import pl.pacinho.failuremanagementsystem.ui.model.SearchOptionsDto;
import pl.pacinho.failuremanagementsystem.ui.model.SearchResultDto;
import pl.pacinho.failuremanagementsystem.ui.model.SearchResultItem;
import pl.pacinho.failuremanagementsystem.ui.model.mapper.SearchResultMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final SearchRepository searchRepository;

    public List<SearchResultDto> search(SearchOptionsDto searchOptionsDto) {
        if (searchOptionsDto.getSelectedTypes() == null || searchOptionsDto.getSelectedTypes().isEmpty())
            searchOptionsDto.init();

        return searchRepository.search(searchOptionsDto)
                .stream()
                .collect(Collectors.groupingBy(SearchResultItem::number))
                .values()
                .stream()
                .map(SearchResultMapper::parseDto)
                .toList();
    }
}