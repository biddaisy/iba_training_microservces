package eu.ibagroup.web.advice;

import eu.ibagroup.common.exception.EventFoundException;
import eu.ibagroup.common.exception.EventNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EventFailedAdvice {
    @ResponseBody
    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String eventNotFoundHandler(EventNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(EventFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String eventFoundHandler(EventFoundException ex) {
        return ex.getMessage();
    }
}
