package eu.ibagroup.service;

import eu.ibagroup.collection.Event;
import eu.ibagroup.exception.DuplicateEntityException;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.*;

import static eu.ibagroup.exception.DuplicateEntityException.throwDuplicateEntityException;
import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Service to work with events
 *
 * @author Mikalai Zaikin (mzaikin@ibagroup.eu)
 * @since 4Q2022
 */
@Service
public class EventService {

    private final Map<String, Event> events = new HashMap<>();

    public List<Event> getEvents() {
        return events.values().stream()
                .sorted(Comparator.comparing(Event::getStartDate))
                .toList();
    }

    public Optional<Event> getEvent(String id) {
        return Optional.ofNullable(events.get(id));
    }

    public Event createEvent(Event event) throws DuplicateEntityException {
        val id = event.getId();
        return !hasEvent(id) ? createEvent(event, id).orElseThrow() : throwDuplicateEntityException(id);
    }

    private boolean hasEvent(String id) {
        return events.containsKey(id);
    }

    public Optional<Event> updateOrCreateEvent(Event event, String id) {
        return Optional.ofNullable(events.get(id))
                .map(updatableEvent -> updateEvent(event, updatableEvent))
                .orElseGet(() -> createEvent(event, id));
    }

    private Optional<Event> createEvent(Event event, String id) {
        event.setId(id);
        events.put(id, event);
        return Optional.of(event);
    }

    private static Optional<Event> updateEvent(Event event, Event updatableEvent) {
        copyProperties(event, updatableEvent, "id", "currentNumOfParticipants");
        return Optional.empty();
    }

    public Optional<Event> deleteById(String id) {
        return Optional.ofNullable(events.remove(id));
    }

}
