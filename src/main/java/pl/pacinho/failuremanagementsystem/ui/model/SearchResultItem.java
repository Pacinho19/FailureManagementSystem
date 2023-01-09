package pl.pacinho.failuremanagementsystem.ui.model;

import lombok.*;
import pl.pacinho.failuremanagementsystem.ui.model.enums.SearchType;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchResultItem {
    private BigDecimal number;
    private String title;

    @Setter(AccessLevel.NONE)
    private SearchType type;

    public void setType(String type) {
        this.type = SearchType.fromString(type);
    }
}