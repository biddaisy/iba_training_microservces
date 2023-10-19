package eu.ibagroup.web.controller;

import eu.ibagroup.common.service.ConfirmationService;
import eu.ibagroup.common.service.SendMailService;
import eu.ibagroup.common.service.SessionService;
import eu.ibagroup.web.exception.ConfirmationNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
public class ConfirmationController {

    private final ConfirmationService confirmationService;
    private final SendMailService sendMailService;
    private final SessionService sessionService;

    @GetMapping("/confirmation/{id}")
    ResponseEntity<String> one(@PathVariable String id) throws MessagingException {
        val confirmation = confirmationService
                .findConfirmation(id)
                .orElseThrow(() -> new ConfirmationNotFoundException(id));
        sessionService.confirmUser(confirmation.getChatId());
        sendMailService.sendNotificationSuccess(confirmation.getEmail());
        return ResponseEntity.ok("You successfully confirmed your email !");
    }

}
