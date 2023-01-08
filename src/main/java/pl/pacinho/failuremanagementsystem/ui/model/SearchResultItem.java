package pl.pacinho.failuremanagementsystem.ui.model;

import pl.pacinho.failuremanagementsystem.ui.model.enums.SearchType;

public record SearchResultItem(long number, String title, SearchType type) {
}