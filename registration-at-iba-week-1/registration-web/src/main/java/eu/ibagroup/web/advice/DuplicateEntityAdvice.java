package eu.ibagroup.web.advice;

import eu.ibagroup.exception.DuplicateEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DuplicateEntityAdvice {

    @ResponseBody
    @ExceptionHandler(DuplicateEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String duplicateEntityHandler(DuplicateEntityException ex) {
        return ex.getMessage();
    }
}
