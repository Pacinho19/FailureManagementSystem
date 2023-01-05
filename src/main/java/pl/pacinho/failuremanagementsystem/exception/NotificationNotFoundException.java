package pl.pacinho.failuremanagementsystem.exception;

import javax.persistence.EntityNotFoundException;

public class NotificationNotFoundException extends EntityNotFoundException {
    public NotificationNotFoundException(long id) {
        super("Notification id " + id + "not found");
    }
}