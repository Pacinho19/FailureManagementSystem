package pl.pacinho.failuremanagementsystem.ui.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class NotificationAlertDto {

    private String id;
    private String text;
    private long count;

    public NotificationAlertDto(String text, long count) {
        this.text = text;
        this.count = count;
        this.id = UUID.randomUUID().toString();
    }
}