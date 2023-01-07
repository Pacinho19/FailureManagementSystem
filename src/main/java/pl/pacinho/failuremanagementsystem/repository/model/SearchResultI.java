package pl.pacinho.failuremanagementsystem.repository.model;

import pl.pacinho.failuremanagementsystem.ui.model.enums.SearchResultType;

public interface SearchResultI {

    long getNumber();

    String getTitle();

    SearchResultType getType();
}