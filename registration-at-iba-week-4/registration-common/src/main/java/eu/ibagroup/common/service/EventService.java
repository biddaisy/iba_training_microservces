package eu.ibagroup.common.service;

import eu.ibagroup.common.mongo.EventRepository;
import eu.ibagroup.common.mongo.collection.Event;
import eu.ibagroup.common.mongo.collection.Registration;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> getActualEvents() {
        val now = LocalDate.now();
        return eventRepository.getAllActual(now);
    }

    public Optional<Event> getEvent(String id) {
        return eventRepository.findById(id);
    }

    public void reserveOneSeat(String id) {
        eventRepository
                .findById(id)
                .ifPresent(this::reserveOneSeat);
    }

    private void reserveOneSeat(Event event) {
        event.setCurrentNumOfParticipants(event.getCurrentNumOfParticipants() + 1);
        eventRepository.save(event);
    }

    public void freeOneSeat(String eventId) {
        eventRepository
                .findById(eventId)
                .ifPresent(this::freeOneSeat);
    }

    private void freeOneSeat(Event event) {
        event.setCurrentNumOfParticipants(event.getCurrentNumOfParticipants() - 1);
        eventRepository.save(event);
    }

    public void freeOneSeat(@NonNull List<Registration> registrations) {
        val eventIds = registrations.stream().map(Registration::getEventId).collect(Collectors.toSet());
        val events = eventRepository.getManyFromSet(eventIds);
        eventRepository.freeOneSeat(events);
    }

    public Event createEvent(Event e) {
        return eventRepository.insert(e);
    }

    public Event updateEvent(Event event, String eventId) {
        return eventRepository
                .findById(eventId)
                .map(updatableEvent -> updateEvent(event, updatableEvent))
                .orElseGet(() -> createEvent(event, eventId));
    }

    private Event createEvent(Event event, String id) {
        event.setId(id);
        return eventRepository.insert(event);
    }

    private Event updateEvent(Event sourceEvent, Event targetEvent) {
        targetEvent.setName(sourceEvent.getName());
        targetEvent.setStartDate(sourceEvent.getStartDate());
        targetEvent.setEndDate(sourceEvent.getEndDate());
        targetEvent.setMaxNumOfParticipants((sourceEvent.getMaxNumOfParticipants()));
        return eventRepository.save(targetEvent);
    }

    public void deleteById(String id) {
        eventRepository.deleteById(id);
    }
}
