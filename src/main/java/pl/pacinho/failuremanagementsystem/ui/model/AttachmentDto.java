package pl.pacinho.failuremanagementsystem.ui.model;

import pl.pacinho.failuremanagementsystem.model.dto.UserDto;
import pl.pacinho.failuremanagementsystem.model.enums.AttachmentSource;

import java.time.LocalDateTime;

public record AttachmentDto(Long id, String baseName, LocalDateTime uploadDate, UserDto user, AttachmentSource source) {
}
