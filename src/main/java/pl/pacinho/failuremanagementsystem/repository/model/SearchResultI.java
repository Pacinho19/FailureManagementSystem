package pl.pacinho.failuremanagementsystem.repository.model;

import pl.pacinho.failuremanagementsystem.ui.model.enums.SearchType;

public interface SearchResultI {

    long getNumber();

    String getTitle();

    SearchType getType();
}