package pl.pacinho.failuremanagementsystem.ui.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class NotificationAlertDto {

    private String id;
    private String text;

    public NotificationAlertDto(String text) {
        this.text = text;
        this.id = UUID.randomUUID().toString();
    }
}