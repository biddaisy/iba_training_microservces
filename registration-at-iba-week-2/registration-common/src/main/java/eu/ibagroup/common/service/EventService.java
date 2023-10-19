package eu.ibagroup.common.service;

import eu.ibagroup.common.exception.EventFoundException;
import eu.ibagroup.common.exception.EventNotFoundException;
import eu.ibagroup.common.mongo.EventRepository;
import eu.ibagroup.common.mongo.collection.Event;
import eu.ibagroup.common.mongo.collection.Registration;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
        LocalDate now = LocalDate.now();
        return eventRepository.getAllActual(now);
    }

    public Optional<Event> getEvent(@NonNull String id) {
        return eventRepository.findById(id);
    }

    public Event reserveOneSeat(@NonNull String eventId) {
        return eventRepository.findById(eventId)
                .map(event -> {
                    event.setCurrentNumOfParticipants(event.getCurrentNumOfParticipants() + 1);
                    return eventRepository.save(event);
                })
                .orElseThrow(()->new EventNotFoundException(eventId));
    }

    public Event freeOneSeat(@NonNull String eventId) {
        return eventRepository.findById(eventId)
                .map(event -> {
                    event.setCurrentNumOfParticipants(event.getCurrentNumOfParticipants() - 1);
                    return eventRepository.save(event);
                })
                .orElseThrow(()->new EventNotFoundException(eventId));
    }

    public void freeOneSeat(@NonNull List<Registration> registrations) {
        Set<String> eventIds = registrations.stream().map(Registration::getEventId).collect(Collectors.toSet());
        List<Event> events = eventRepository.getAllByIds(eventIds);
        eventRepository.freeOneSeat(events);
    }


    public Event createEvent(@NonNull Event event) {
        validateIfExists(event.getId());
        return eventRepository.insert(event);
    }

    private void validateIfExists(String eventId) {
        eventRepository
                .findById(eventId)
                .ifPresent(e -> {
                    throw new EventFoundException(e.getId());
                });
    }

    public Event updateEvent(@NonNull Event event, @NonNull String eventId) {
        return eventRepository.findById(eventId)
                .map(foundEvent -> updateEvent(event, foundEvent))
                .orElseGet(() -> createEvent(event, eventId));
    }

    private Event createEvent(Event event, String eventId) {
        event.setId(eventId);
        return eventRepository.insert(event);
    }

    private Event updateEvent(Event sourceEvent, Event targetEvent) {
        targetEvent.setName(sourceEvent.getName());
        targetEvent.setStartDate(sourceEvent.getStartDate());
        targetEvent.setEndDate(sourceEvent.getEndDate());
        targetEvent.setMaxNumOfParticipants((sourceEvent.getMaxNumOfParticipants()));
        return eventRepository.save(targetEvent);
    }

    public Event deleteById(@NonNull String eventId) {
        return eventRepository
                .findById(eventId).map( event -> {
                        eventRepository.deleteById(eventId);
                        return event;
                })
                .orElseThrow(()->new EventNotFoundException(eventId));
    }
}