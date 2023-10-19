package eu.ibagroup.web.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String id) {
        super("Could not find event: " + id);
    }
}
