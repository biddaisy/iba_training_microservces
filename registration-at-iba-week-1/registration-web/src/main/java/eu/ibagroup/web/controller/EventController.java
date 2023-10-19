package eu.ibagroup.web.controller;

import eu.ibagroup.exception.DuplicateEntityException;
import eu.ibagroup.web.exception.EventNotFoundException;
import eu.ibagroup.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import eu.ibagroup.collection.Event;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * REST endpoint to handle events
 *
 * @author Mikalai Zaikin (mzaikin@ibagroup.eu)
 * @since 4Q202
 */
@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/event")
    List<Event> getAllEvents() {
        return eventService.getEvents();
    }

    @GetMapping("/event/{id}")
    Event getOneEvent(@PathVariable String id) {
        return eventService.getEvent(id).orElseThrow(() -> new EventNotFoundException(id));
    }

    @PostMapping("/event")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Object> createEvent(@RequestBody Event event) throws DuplicateEntityException {
        Event createdEvent = eventService.createEvent(event);
        URI location = getLocation(createdEvent);
        return ResponseEntity.created(location).build();
    }

    private static URI getLocation(Event createdEvent) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdEvent.getId()).toUri();
    }

    @PutMapping("/event/{id}")
    ResponseEntity<Object> updateOrCreateEvent(@RequestBody Event event, @PathVariable String id) {
        return eventService.updateOrCreateEvent(event, id)
                .map(e -> {
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
                    return ResponseEntity.created(location).build();
                })
                .orElseGet(() -> ResponseEntity.ok().build());

    }

    @DeleteMapping("/event/{id}")
    ResponseEntity<Object> deleteEvent(@PathVariable String id) {
        return eventService.deleteById(id)
                .map(e -> ResponseEntity.noContent().build())
                .orElseThrow(() -> new EventNotFoundException(id));
    }
}
