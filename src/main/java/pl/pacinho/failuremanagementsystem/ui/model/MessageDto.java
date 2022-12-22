package pl.pacinho.failuremanagementsystem.ui.model;

import java.time.LocalDateTime;

public record MessageDto( String userName,LocalDateTime dateTime,String message ) {
}
