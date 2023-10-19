package eu.ibagroup.common.exception;

public class EventFoundException extends RuntimeException {
    public EventFoundException(String id) {
        super("Event found: " + id);
    }
}
