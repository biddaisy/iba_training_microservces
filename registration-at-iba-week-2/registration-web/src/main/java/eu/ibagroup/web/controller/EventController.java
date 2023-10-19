package eu.ibagroup.web.controller;

import eu.ibagroup.common.exception.EventNotFoundException;
import eu.ibagroup.common.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import eu.ibagroup.common.mongo.collection.Event;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/event")
    List<Event> getAllEvents() {
        return eventService.getActualEvents();
    }

    @GetMapping("/event/{id}")
    Event getOneEvent(@PathVariable String id) {
        return eventService.getEvent(id).orElseThrow(() -> new EventNotFoundException(id));
    }

    @PostMapping("/event")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Object> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        URI location = createLocation(createdEvent);
        return ResponseEntity.created(location).build();
    }

    private static URI createLocation(Event createdEvent) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdEvent.getId()).toUri();
    }

    @PutMapping("/event/{id}")
    ResponseEntity<Object> updateEvent(@RequestBody Event event, @PathVariable String eventId) {
        eventService.updateEvent(event, eventId);
        URI location = getLocation();
        return ResponseEntity.created(location).build();
    }

    private static URI getLocation() {
        return ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    }

    @DeleteMapping("/event/{id}")
    ResponseEntity<Void> deleteEvent(@PathVariable String eventId) {
        eventService.deleteById(eventId);
        return ResponseEntity.noContent().build();
    }
}
