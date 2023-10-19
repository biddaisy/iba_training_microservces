package eu.ibagroup.web.controller;

import eu.ibagroup.collection.Confirmation;
import eu.ibagroup.exception.DuplicateEntityException;
import eu.ibagroup.service.ConfirmationService;
import eu.ibagroup.service.SendMailService;
import eu.ibagroup.web.exception.ConfirmationNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.net.URI;

/**
 *  REST endpoint to handle confirmations
 *
 *  @author Mikalai Zaikin (mzaikin@ibagroup.eu)
 *  @since 4Q2022
 */
@RestController
@RequiredArgsConstructor
public class ConfirmationController {

    private final ConfirmationService confirmationService;
    private final SendMailService sendMailService;

    @PostMapping("/confirmation")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Object> createEvent(@RequestBody Confirmation confirmation) throws DuplicateEntityException, MessagingException {
        Confirmation createdConfirmation = confirmationService.createConfirmation(confirmation);
        sendMailService.sendNotification(createdConfirmation.getId(), createdConfirmation.getEmail());
        URI location = getLocation(createdConfirmation);
        return ResponseEntity.created(location).build();
    }

    private static URI getLocation(Confirmation createdConfirmation) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdConfirmation.getId()).toUri();
    }

    @GetMapping("/confirmation/{id}")
    ResponseEntity<String> getOneConfirmation(@PathVariable String id) throws MessagingException {
        val email = getEmail(id);
        sendMailService.sendNotificationSuccess(email);
        return ResponseEntity.ok("You successfully confirmed your email !");
    }

    private String getEmail(String id) {
        return confirmationService
                .findConfirmation(id)
                .orElseThrow(() -> new ConfirmationNotFoundException(id))
                .getEmail();
    }
}
