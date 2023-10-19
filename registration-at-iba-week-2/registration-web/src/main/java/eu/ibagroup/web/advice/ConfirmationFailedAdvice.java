package eu.ibagroup.web.advice;

import eu.ibagroup.web.exception.ConfirmationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.mail.MessagingException;

@ControllerAdvice
public class ConfirmationFailedAdvice {
    @ResponseBody
    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String messagingExceptionHandler(MessagingException ex) {
        return ex.getMessage();
    }
}
