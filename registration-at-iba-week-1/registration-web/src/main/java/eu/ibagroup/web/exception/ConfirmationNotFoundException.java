package eu.ibagroup.web.exception;

/**
 *  Signals that a confirmation cannot be found
 *
 *  @author Mikalai Zaikin (mzaikin@ibagroup.eu)
 *  @since 4Q2022
 */
public class ConfirmationNotFoundException extends RuntimeException {
    public ConfirmationNotFoundException(String id) {
        super("Could not find confirmation " + id);
    }
}
