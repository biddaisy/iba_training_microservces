package eu.ibagroup.web.exception;

public class ConfirmationNotFoundException extends RuntimeException {
    public ConfirmationNotFoundException(String id) {
        super("Could not find confirmation " + id);
    }
}
