package pl.pacinho.failuremanagementsystem.ui.model;

import pl.pacinho.failuremanagementsystem.model.enums.MessageType;

import java.time.LocalDateTime;

public record MessageDto(String userName, LocalDateTime dateTime, String message, MessageType type) {
}
