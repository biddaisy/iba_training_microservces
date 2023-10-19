package eu.ibagroup.web.advice;

import eu.ibagroup.web.exception.ConfirmationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ConfirmationNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ConfirmationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String confirmationNotFoundHandler(ConfirmationNotFoundException ex) {
        return ex.getMessage();
    }
}
