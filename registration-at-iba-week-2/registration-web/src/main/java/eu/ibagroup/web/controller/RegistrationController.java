package eu.ibagroup.web.controller;

import eu.ibagroup.common.mongo.collection.Registration;
import eu.ibagroup.common.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    public static final String EVENT_ID = "eventId";
    private final RegistrationService registrationService;

    @GetMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Registration> getRegistrations(@RequestParam(value = EVENT_ID, required = false) String eventId) {
        return Optional.ofNullable(eventId).map(registrationService::getRegistrationsByEventId).orElse(emptyList());
    }

    @GetMapping(value = "/registration", produces = MediaType.TEXT_PLAIN_VALUE)
    String getRegistrationsAsText(@RequestParam(value = EVENT_ID, required = false) String eventId) {
        return getRegistrations(eventId).stream()
                .map(Registration::asRestString)
                .collect(Collectors.joining("\n"));
    }
}
