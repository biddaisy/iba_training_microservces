package eu.ibagroup.web.controller;

import eu.ibagroup.web.exception.EventNotFoundException;
import eu.ibagroup.common.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import eu.ibagroup.common.mongo.collection.Event;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

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
    ResponseEntity<Void> createEvent(@RequestBody Event event) {
        val createdEvent = eventService.createEvent(event);
        val location = getUri(createdEvent);
        return ResponseEntity.created(location).build();
    }

    private static URI getUri(Event createdEvent) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdEvent.getId()).toUri();
    }

    @PutMapping("/event/{id}")
    ResponseEntity<Object> updateEvent(@RequestBody Event event, @PathVariable String id) {
        eventService.updateEvent(event, id);
        val location = getUri();
        return ResponseEntity.created(location).build();
    }

    private static URI getUri() {
        return ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    }

    @DeleteMapping("/event/{id}")
    ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
