package pl.pacinho.failuremanagementsystem.ui.model;

import pl.pacinho.failuremanagementsystem.ui.model.enums.SearchResultType;

public record SearchResultItem(long number, String title, SearchResultType type) {
}