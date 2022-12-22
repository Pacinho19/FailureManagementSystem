package pl.pacinho.failuremanagementsystem.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(long number) {
        super("Task " + number + " not found");
    }
}
