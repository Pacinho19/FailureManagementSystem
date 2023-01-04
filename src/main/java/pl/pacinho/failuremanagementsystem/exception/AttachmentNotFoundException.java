package pl.pacinho.failuremanagementsystem.exception;

import javax.persistence.EntityNotFoundException;

public class AttachmentNotFoundException extends EntityNotFoundException {
    public AttachmentNotFoundException() {
        super("Selected attachment file not found!");
    }
}