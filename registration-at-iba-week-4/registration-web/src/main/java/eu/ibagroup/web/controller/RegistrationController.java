package eu.ibagroup.web.controller;

import eu.ibagroup.common.mongo.collection.Registration;
import eu.ibagroup.common.service.RegistrationService;
import eu.ibagroup.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final SessionService sessionService;

    @GetMapping(
            value = "/registration",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Registration> getAllRegistered(@RequestParam(name = "id") Optional<String> eventId) {
        return eventId.map(registrationService::getRegistrations).orElse(List.of());
    }

    @GetMapping(
            value = "/registration",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    String getAllRegisteredText(@RequestParam(name = "id") Optional<String> eventId) {
        var registrations = getAllRegistered(eventId);
        return registrations.stream().map(Registration::asRestString).collect(Collectors.joining("\n"));
    }
}
