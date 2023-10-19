package eu.ibagroup.web.exception;

/**
 *  Signals that an event cannot be found
 *
 *  @author Mikalai Zaikin (mzaikin@ibagroup.eu)
 *  @since 4Q2022
 */
public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String id) {
        super("Could not find event: " + id);
    }
}
