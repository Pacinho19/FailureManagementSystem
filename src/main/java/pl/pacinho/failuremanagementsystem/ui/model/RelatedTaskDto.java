package pl.pacinho.failuremanagementsystem.ui.model;

import pl.pacinho.failuremanagementsystem.model.enums.Status;

public record RelatedTaskDto(long number, String title, Status status) {
}
