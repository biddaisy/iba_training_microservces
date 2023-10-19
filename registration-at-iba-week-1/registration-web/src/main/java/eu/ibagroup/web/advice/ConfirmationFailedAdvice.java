package eu.ibagroup.web.advice;

import eu.ibagroup.web.exception.ConfirmationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.mail.MessagingException;

/**
 *  Web handler for case when confirmation cannot be processed
 *
 *  @author Mikalai Zaikin (mzaikin@ibagroup.eu)
 *  @since 4Q2022
 */
@ControllerAdvice
public class ConfirmationFailedAdvice {
    @ResponseBody
    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String messagingHandler(MessagingException ex) {
        return ex.getMessage();
    }
}
