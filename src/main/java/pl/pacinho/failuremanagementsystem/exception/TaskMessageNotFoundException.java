package pl.pacinho.failuremanagementsystem.exception;

import javax.persistence.EntityNotFoundException;

public class TaskMessageNotFoundException extends EntityNotFoundException {

    public TaskMessageNotFoundException(long number) {
        super("Task message number " + number + " not found");
    }
}
